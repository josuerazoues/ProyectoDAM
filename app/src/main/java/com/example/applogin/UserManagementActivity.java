package com.example.applogin;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private TableLayout userTable;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // Inicializar vistas
        userTable = findViewById(R.id.userTable);
        if (userTable == null) {
            throw new IllegalStateException("TableLayout con ID 'userTable' no encontrado en el layout");
        }

        TextView appTitle = findViewById(R.id.appTitle);
        appTitle.setText("Gestión de Usuarios");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Gestión de Usuarios");
        }

        // Obtener instancia de la base de datos
        db = AppDatabase.getInstance(getApplicationContext());

        // Cargar usuarios
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