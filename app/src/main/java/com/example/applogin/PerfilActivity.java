package com.example.applogin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class PerfilActivity extends MenuLateral {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imgURI;

    private ImageView profileImage;
    private TextInputEditText etName, etEmail, etPhone, etPassword;
    private Button btnEdit, btnSave;
    private AppDatabase db;
    private Usuario usuarioActual;
    private int userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Configurar toolbar y drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.nav_view);

        // Enlazar vistas
        profileImage = findViewById(R.id.profile_image);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        btnEdit = findViewById(R.id.btn_edit);
        btnSave = findViewById(R.id.btn_save);

        // Restaurar imagen seleccionada al recrear vista
        if (savedInstanceState != null) {
            String uriGuardada = savedInstanceState.getString("img_uri");
            if (uriGuardada != null) {
                imgURI = Uri.parse(uriGuardada);
                profileImage.setImageURI(imgURI);
            }
        }

        // Permitir selección de imagen al hacer clic en la imagen
        profileImage.setOnClickListener(view -> verificarYPedirPermisos());

        // Botón para editar
        btnEdit.setOnClickListener(v -> habilitarEdicion(true));

        // Botón para guardar cambios
        btnSave.setOnClickListener(v -> guardarCambios());

        // Inicializar DB y cargar datos
        db = AppDatabase.getInstance(getApplicationContext());
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        new Thread(() -> {
            usuarioActual = db.usuarioDao().obtenerPorId(userId);
            if (usuarioActual != null) {
                runOnUiThread(() -> {
                    etName.setText(usuarioActual.getNombre());
                    etEmail.setText(usuarioActual.getCorreo());
                    etPhone.setText(usuarioActual.getTelefono());
                    etPassword.setText(usuarioActual.getPassword());
                });
            }
        }).start();
    }

    private void habilitarEdicion(boolean habilitar) {
        etName.setEnabled(habilitar);
        etEmail.setEnabled(habilitar);
        etPhone.setEnabled(habilitar);
        etPassword.setEnabled(habilitar);
        btnSave.setVisibility(habilitar ? View.VISIBLE : View.GONE);
    }

    private void verificarYPedirPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 101);
            } else {
                abrirGaleria();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            } else {
                abrirGaleria();
            }
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imgURI = data.getData();
            profileImage.setImageURI(imgURI);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (imgURI != null) {
            outState.putString("img_uri", imgURI.toString());
        }
    }

    private void guardarCambios() {
        String nombre = etName.getText().toString().trim();
        String correo = etEmail.getText().toString().trim();
        String telefono = etPhone.getText().toString().trim();
        String contrasena = etPassword.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            usuarioActual.setNombre(nombre);
            usuarioActual.setCorreo(correo);
            usuarioActual.setTelefono(telefono);
            usuarioActual.setPassword(contrasena);
            db.usuarioDao().actualizar(usuarioActual);

            runOnUiThread(() -> {
                Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                habilitarEdicion(false);
            });
        }).start();
    }
}
