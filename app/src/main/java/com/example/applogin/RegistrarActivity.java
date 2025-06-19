package com.example.applogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrarActivity extends AppCompatActivity {

    EditText etNuevoUsuario, etNuevoPassword, etConfirmarPassword, etEmail;
    Button btnGuardar, btnRegresar;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevoPassword = findViewById(R.id.etNuevoPassword);
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword);
        etEmail = findViewById(R.id.etEmail);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnRegresar = findViewById(R.id.btnRegresar);

        prefs = getSharedPreferences("usuarios", MODE_PRIVATE);

        btnGuardar.setOnClickListener(v -> guardarUsuario());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void guardarUsuario() {
        String usuario = etNuevoUsuario.getText().toString().trim();
        String password = etNuevoPassword.getText().toString();
        String confirmar = etConfirmarPassword.getText().toString();
        String email = etEmail.getText().toString().trim();

        if (usuario.length() < 3) {
            mostrar("El usuario debe tener al menos 3 caracteres.");
            return;
        }

        if (password.length() < 5 || !password.matches("[a-zA-Z0-9]+")) {
            mostrar("La contraseña debe ser alfanumerica y tener al menos 5 caracteres.");
            return;
        }

        if (!password.equals(confirmar)) {
            mostrar("Las contraseñas no coinciden.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrar("Correo electrónico no valido.");
            return;
        }

        prefs.edit().putString(usuario, password).apply();
        mostrar("Usuario registrado con exito.");

        etNuevoUsuario.setText("");
        etNuevoPassword.setText("");
        etConfirmarPassword.setText("");
        etEmail.setText("");
    }

    private void mostrar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
