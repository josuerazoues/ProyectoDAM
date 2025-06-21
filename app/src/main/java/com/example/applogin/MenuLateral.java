package com.example.applogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applogin.CitaTaller.CitasTaller;
import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.google.android.material.navigation.NavigationView;

public class MenuLateral extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    protected int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode());
        userId = getIntent().getIntExtra("ID_USUARIO", -1);

        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.navigation_view);
    }

    public void configurarDrawer(int idToolbar, int idDrawerLayout, int idNavView) {
        toolbar = findViewById(idToolbar);
        if (toolbar == null) {
            Log.e("MenuLateral", "Toolbar no encontrado con ID: " + idToolbar);
        } else {
            setSupportActionBar(toolbar);
        }

        drawer = findViewById(idDrawerLayout);
        if (drawer == null) {
            Log.e("MenuLateral", "DrawerLayout no encontrado con ID: " + idDrawerLayout);
        }

        navigationView = findViewById(idNavView);
        if (navigationView == null) {
            Log.e("MenuLateral", "NavigationView no encontrado con ID: " + idNavView);
        }

        if (drawer != null && navigationView != null && toolbar != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);
        } else {
            Log.e("MenuLateral", "Toolbar, Drawer o NavigationView son null. Drawer no configurado.");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_perfil) {
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra("ID_USUARIO", userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_user_management) {
            new Thread(() -> {
                Usuario usuario = AppDatabase.getInstance(getApplicationContext())
                        .usuarioDao()
                        .obtenerPorId(userId);

                runOnUiThread(() -> {
                    if (usuario != null && usuario.getId_rol() == 1) {
                        Intent intent = new Intent(this, UserManagementActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Acceso no autorizado", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();

            return true;

        } else if (id == R.id.citas) {
            Intent intent = new Intent(this, CitasTaller.class);
            intent.putExtra("ID_USUARIO", userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_toggle_theme) {
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            AppCompatDelegate.setDefaultNightMode(
                    nightMode == AppCompatDelegate.MODE_NIGHT_YES ?
                            AppCompatDelegate.MODE_NIGHT_NO :
                            AppCompatDelegate.MODE_NIGHT_YES
            );
            recreate();

        } else if (id == R.id.registro_vehiculo) {
            Intent intent = new Intent(this, RegistrarVehiculo.class);
            intent.putExtra("ID_USUARIO", userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.ver_vehiculos) {
            Intent intent = new Intent(this, ListaVehiculosActivity.class);
            intent.putExtra("ID_USUARIO", userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        // Cierra el drawer en todos los casos si no es null
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.e("MenuLateral", "Drawer es null en onNavigationItemSelected, no se puede cerrar.");
        }

        return true;
    }

}
