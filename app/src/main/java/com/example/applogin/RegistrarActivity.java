package com.example.applogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.example.applogin.base_DAO.UsuarioDao;

public class RegistrarActivity extends AppCompatActivity {

    EditText etNuevoUsuario, etNuevoPassword, etConfirmarPassword, etEmail, etTelefono, rol;
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
        etTelefono = findViewById(R.id.etTelefono);
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
        String telefono = etTelefono.getText().toString().trim();
        String rol = "3";

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

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        UsuarioDao usuarioDao = db.usuarioDao();

        // Verificar si el usuario ya existe
        if (usuarioDao.obtenerPorNombre(usuario) != null) {
            mostrar("El nombre de usuario ya existe.");
            return;
        }

        // Insertar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(usuario, email, telefono, password, 3);
        usuarioDao.insertar(nuevoUsuario);

        mostrar("Usuario registrado con éxito.");

        etNuevoUsuario.setText("");
        etNuevoPassword.setText("");
        etConfirmarPassword.setText("");
        etEmail.setText("");
    }


    private void mostrar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}