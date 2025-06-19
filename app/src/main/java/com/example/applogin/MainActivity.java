package com.example.applogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etPassword;
    Button btnIngresar, btnSalir, btnRegistrar;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnSalir = findViewById(R.id.btnSalir);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        prefs = getSharedPreferences("usuarios", MODE_PRIVATE);
        btnIngresar.setOnClickListener(v -> login());
        btnSalir.setOnClickListener(v -> finishAffinity());
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString();

        String passwordGuardado = prefs.getString(usuario, null);

        if (passwordGuardado != null && passwordGuardado.equals(password)) {
            Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecto.", Toast.LENGTH_SHORT).show();
        }
    }
}
