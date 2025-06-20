package com.example.applogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.google.android.material.navigation.NavigationView;

public class MenuLateral extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    protected int userId = -1; // Si usas ID de usuario, pásalo en los intents

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getIntent().getIntExtra("ID_USUARIO", -1); // O ajusta según cómo pasas el ID
    }

    public void configurarDrawer(int idToolbar, int idDrawerLayout, int idNavView) {
        toolbar = findViewById(idToolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(idDrawerLayout);
        navigationView = findViewById(idNavView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Ya estamos en home
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Cierra la actual
        } else if (id == R.id.nav_perfil) {
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra("ID_USUARIO", userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Cierra la actual
        } else if (id == R.id.nav_user_management) {
            new Thread(() -> {
                Usuario usuario = AppDatabase.getInstance(getApplicationContext())
                        .usuarioDao()
                        .obtenerPorId(userId);

                if (usuario != null) {
                    runOnUiThread(() -> {
                        if (usuario.getId_rol() == 1) { // 1 = Administrador
                            Intent intent = new Intent(this, UserManagementActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish(); // Cierra la actual
                        } else {
                            Toast.makeText(this, "Acceso no autorizado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Cierra la sesión actual
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
