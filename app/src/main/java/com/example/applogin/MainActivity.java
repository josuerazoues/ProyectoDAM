package com.example.applogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Rol;
import com.example.applogin.base_DAO.Usuario;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private Button btnIngresar, btnSalir, btnRegistrar;
    private SharedPreferences prefs;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewModel();
        setupClickListeners();
        setupObservers();

        // Agregar usuario por defecto
        insertarUsuarioPorDefecto();
    }

    private void insertarUsuarioPorDefecto() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Usuario existente = db.usuarioDao().buscarPorNombre("admin");


            // Insertar roles primero si no existen
            if (db.rolDao().contarRoles() == 0) {
                db.rolDao().insertar(new Rol(1, "Administrador"));
                db.rolDao().insertar(new Rol(2, "Mecanico"));
                db.rolDao().insertar(new Rol(3, "Usuario"));
            }

            if (existente == null) {
                Usuario admin = new Usuario();
                admin.setNombre("admin");
                admin.setPassword("12345");
                admin.setCorreo("admin@admin");
                admin.setTelefono("123456789");
                admin.setId_rol(1);
                db.usuarioDao().insertar(admin);
                Log.d("DB_INIT", "Usuario admin creado.");
            } else {
                Log.d("DB_INIT", "Usuario admin ya existe.");
            }
        });
    }

    private void initViews() {
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnSalir = findViewById(R.id.btnSalir);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        prefs = getSharedPreferences("usuarios", MODE_PRIVATE);
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupClickListeners() {
        btnIngresar.setOnClickListener(v -> attemptLogin());
        btnSalir.setOnClickListener(v -> finishAffinity());
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
            startActivity(intent);
        });
    }

    private void setupObservers() {
        loginViewModel.getUsuarioResult().observe(this, usuario -> {
            if (usuario != null) {
                handleSuccessfulLogin(usuario);
            } else {
                if (loginViewModel.getError().getValue() == null) {
                    showToast("Usuario o contraseña incorrecto.");
                }
            }
        });

        loginViewModel.getError().observe(this, error -> {
            if (error != null) {
                showToast(error);
            }
        });
    }

    private void attemptLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (usuario.isEmpty() || password.isEmpty()) {
            showToast("Por favor, ingrese usuario y contraseña.");
            return;
        }

        loginViewModel.loginUser(usuario, password);
    }

    private void handleSuccessfulLogin(Usuario usuario) {
        showToast("Bienvenido " + usuario.getNombre());
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("ID_USUARIO", usuario.getId_usuario());
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}