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
import androidx.core.content.ContextCompat;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;

import java.util.List;

public class UserManagementActivity extends MenuLateral {

    private TableLayout userTable;
    private AppDatabase db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // ✅ Obtener ID de usuario desde el intent
        userId = getIntent().getIntExtra("ID_USUARIO", -1);
        if (userId == -1) {
            Toast.makeText(this, "Error: ID de usuario no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✅ Configurar el Drawer desde la clase base
        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.nav_view);

        // ✅ Configurar título de toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Gestión de Usuarios");
        }

        // ✅ Inicializar tabla
        userTable = findViewById(R.id.userTable);
        if (userTable == null) {
            throw new IllegalStateException("TableLayout con ID 'userTable' no encontrado en el layout");
        }

        // ✅ Instancia de base de datos
        db = AppDatabase.getInstance(getApplicationContext());

        // ✅ Cargar usuarios en tabla
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        new Thread(() -> {
            try {
                List<Usuario> usuarios = db.usuarioDao().obtenerTodos();

                runOnUiThread(() -> {
                    // Limpiar tabla excepto cabecera
                    int childCount = userTable.getChildCount();
                    for (int i = childCount - 1; i >= 1; i--) {
                        userTable.removeViewAt(i);
                    }

                    // Agregar filas de usuarios
                    for (Usuario usuario : usuarios) {
                        TableRow row = new TableRow(this);
                        row.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT
                        ));

                        // Columna: Nombre
                        TextView tvNombre = crearCeldaTabla(usuario.getNombre());
                        row.addView(tvNombre);

                        // Columna: Cargo
                        String cargo = (usuario.getId_rol() == 1) ? "Administrador" : "Usuario";
                        TextView tvCargo = crearCeldaTabla(cargo);
                        row.addView(tvCargo);

                        // Color de fondo alterno
                        int backgroundColor = (userTable.getChildCount() % 2 == 1)
                                ? ContextCompat.getColor(this, android.R.color.background_light)
                                : ContextCompat.getColor(this, android.R.color.darker_gray);

                        row.setBackgroundColor(backgroundColor);

                        // Agregar fila
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
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
        ));
        return textView;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
