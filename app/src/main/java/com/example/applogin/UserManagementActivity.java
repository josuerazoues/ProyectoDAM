package com.example.applogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import java.util.List;

public class UserManagementActivity extends MenuLateral{

    private TableLayout userTable;
    private AppDatabase db;
    private int userId;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar Drawer
        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.nav_view);


        // Configurar título si es necesario
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Gestión de Usuarios");
        }

        // Inicializar vista de tabla
        userTable = findViewById(R.id.userTable);
        if (userTable == null) {
            throw new IllegalStateException("TableLayout con ID 'userTable' no encontrado en el layout");
        }

        // Obtener instancia de Room
        db = AppDatabase.getInstance(getApplicationContext());

        // Cargar datos de usuarios
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        new Thread(() -> {
            try {
                List<Usuario> usuarios = db.usuarioDao().obtenerTodos();

                runOnUiThread(() -> {
                    // Limpiar tabla (excepto la fila de cabecera)
                    int childCount = userTable.getChildCount();
                    for (int i = childCount - 1; i >= 1; i--) {
                        userTable.removeViewAt(i);
                    }

                    // Agregar usuarios a la tabla
                    for (Usuario usuario : usuarios) {
                        TableRow row = new TableRow(this);
                        row.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));

                        // Celda Nombre
                        TextView tvNombre = crearCeldaTabla(usuario.getNombre());
                        row.addView(tvNombre);

                        // Celda Cargo (Rol)
                        String cargo = "Usuario"; // Valor por defecto
                        if (usuario.getId_rol() == 1) {
                            cargo = "Administrador";
                        }
                        TextView tvCargo = crearCeldaTabla(cargo);
                        row.addView(tvCargo);

                        // Alternar color de fondo para mejor legibilidad
                        if (userTable.getChildCount() % 2 == 1) {
                            row.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                        } else {
                            row.setBackgroundColor(getResources().getColor(android.R.color.white));
                        }

                        userTable.addView(row);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private TextView crearCeldaTabla(String texto) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setPadding(12, 12, 12, 12);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(
                0, // 0 para usar weight
                TableRow.LayoutParams.WRAP_CONTENT,
                1f)); // Peso 1 para distribuir espacio
        return textView;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}