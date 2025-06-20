package com.example.applogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.example.applogin.base_DAO.UsuarioDao;

import java.util.concurrent.Executors;

public class RegistrarActivity extends AppCompatActivity {

    private EditText etNuevoUsuario, etNuevoPassword, etConfirmarPassword, etEmail, etTelefono;
    private Button btnGuardar, btnRegresar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        initViews();

        btnGuardar.setOnClickListener(v -> guardarUsuario());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void initViews() {
        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevoPassword = findViewById(R.id.etNuevoPassword);
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnRegresar = findViewById(R.id.btnRegresar);
        prefs = getSharedPreferences("usuarios", MODE_PRIVATE);
    }

    private void guardarUsuario() {
        String usuario = etNuevoUsuario.getText().toString().trim();
        String password = etNuevoPassword.getText().toString();
        String confirmar = etConfirmarPassword.getText().toString();
        String email = etEmail.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        int rolId = 3; // Usuario común

        if (usuario.length() < 3) {
            mostrar("El usuario debe tener al menos 3 caracteres.");
            return;
        }

        if (password.length() < 5 || !password.matches("[a-zA-Z0-9]+")) {
            mostrar("La contraseña debe ser alfanumérica y tener al menos 5 caracteres.");
            return;
        }

        if (!password.equals(confirmar)) {
            mostrar("Las contraseñas no coinciden.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrar("Correo electrónico no válido.");
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            UsuarioDao usuarioDao = db.usuarioDao();

            if (usuarioDao.obtenerPorNombre(usuario) != null) {
                runOnUiThread(() -> mostrar("El nombre de usuario ya existe."));
                return;
            }

            Usuario nuevoUsuario = new Usuario(usuario, email, telefono, password, rolId);
            usuarioDao.insertar(nuevoUsuario);

            runOnUiThread(() -> {
                mostrar("Usuario registrado con éxito.");
                limpiarCampos();
            });
        });
    }

    private void limpiarCampos() {
        etNuevoUsuario.setText("");
        etNuevoPassword.setText("");
        etConfirmarPassword.setText("");
        etEmail.setText("");
        etTelefono.setText("");
    }

    private void mostrar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
