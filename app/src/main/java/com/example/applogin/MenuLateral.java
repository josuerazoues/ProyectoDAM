package com.example.applogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import Log for debugging
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
        //setContentView(R.layout.activity_main); // Make sure this layout has the drawer, toolbar, and nav view
        userId = getIntent().getIntExtra("ID_USUARIO", -1);

        // *** Call configurarDrawer here with the correct IDs ***
        // Replace R.id.toolbar, R.id.drawer_layout, and R.id.nav_view
        // with the actual IDs from your R.layout.activity_main XML file.
        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.nav_view);
    }

    public void configurarDrawer(int idToolbar, int idDrawerLayout, int idNavView) {
        toolbar = findViewById(idToolbar);
        if (toolbar == null) {
            Log.e("MenuLateral", "Toolbar not found with ID: " + idToolbar);
            // You might want to throw an exception or handle this more gracefully
            // depending on whether the toolbar is critical.
        } else {
            setSupportActionBar(toolbar);
        }


        drawer = findViewById(idDrawerLayout);
        if (drawer == null) {
            Log.e("MenuLateral", "DrawerLayout not found with ID: " + idDrawerLayout);
            // This is critical, if the drawer is null, the app will crash later.
            // Consider throwing an IllegalStateException or handling it.
            // For now, onNavigationItemSelected will still crash if it's null.
        }

        navigationView = findViewById(idNavView);
        if (navigationView == null) {
            Log.e("MenuLateral", "NavigationView not found with ID: " + idNavView);
        }


        // It's good practice to check if these views are null before using them,
        // especially the drawer, to prevent crashes if IDs are incorrect or views are missing.
        if (drawer != null && navigationView != null && toolbar != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);
        } else {
            Log.e("MenuLateral", "One or more views (Toolbar, Drawer, NavigationView) are null. Drawer setup incomplete.");
            // You might want to show a Toast or disable navigation features if setup fails.
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // ... (your existing if-else if logic for item clicks) ...
        // Example:
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

                if (usuario != null) {
                    runOnUiThread(() -> {
                        if (usuario.getId_rol() == 1) { // Example: Admin role
                            Intent intent = new Intent(this, UserManagementActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Acceso no autorizado", Toast.LENGTH_SHORT).show();
                            // Keep the drawer open if access is not authorized for user management
                            // and the user is already on a page that needs the drawer.
                            // However, if finish() was called above, this won't matter much.
                            // Consider the flow carefully.

                        }
                    });
                }
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
            // No need to close drawer here if recreate() handles the UI refresh
        } else if (id == R.id.registro_vehiculo) {
            Intent intent = new Intent(this, RegistrarVehiculo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


        // Only close the drawer if it's not null and an actual navigation happened
        // or if you always want to close it after any item click.
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.e("MenuLateral", "Drawer is null in onNavigationItemSelected, cannot close.");
        }
        return true;
    }
}