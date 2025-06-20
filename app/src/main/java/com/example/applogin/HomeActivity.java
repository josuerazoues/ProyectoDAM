package com.example.applogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtener ID de usuario
        userId = getIntent().getIntExtra("ID_USUARIO", -1);

        // Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar Navigation Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, android.R.color.black));

        // Cargar datos del usuario
        if (userId != -1) {
            cargarUsuario(userId);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Ya estamos en home
        } else if (id == R.id.nav_perfil) {
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra("ID_USUARIO", userId);
            startActivity(intent);
        } else if (id == R.id.nav_user_management) {
                new Thread(() -> {
                    Usuario usuario = AppDatabase.getInstance(getApplicationContext())
                            .usuarioDao()
                            .obtenerPorId(userId);

                    runOnUiThread(() -> {
                        if (usuario != null && usuario.getId_rol() == 1) { // 1 = Administrador
                            Intent intent = new Intent(this, UserManagementActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Acceso no autorizado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
        } else if (id == R.id.nav_logout) {
            // Forma más segura de cerrar sesión
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cargarUsuario(int idUsuario) {
        new Thread(() -> {
            try {
                Usuario usuario = AppDatabase.getInstance(getApplicationContext())
                        .usuarioDao()
                        .obtenerPorId(idUsuario);

                runOnUiThread(() -> {
                    NavigationView navView = findViewById(R.id.nav_view);

                    // Verifica que el header exista
                    if (navView != null && navView.getHeaderCount() > 0) {
                        View headerView = navView.getHeaderView(0);

                        // Verifica que los TextView existan
                        TextView tvNombre = headerView.findViewById(R.id.nombreUsuario);
                        TextView tvEmail = headerView.findViewById(R.id.tvEmailUsuario);

                        if (tvNombre != null && tvEmail != null && usuario != null) {
                            tvNombre.setText(usuario.getNombre());
                            tvEmail.setText(usuario.getCorreo());
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("HomeActivity", "Error cargando usuario", e);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}