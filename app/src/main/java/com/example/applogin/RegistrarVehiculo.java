package com.example.applogin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;
import com.example.applogin.base_DAO.Vehiculo;
import com.example.applogin.base_DAO.UsuarioDao;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class RegistrarVehiculo extends MenuLateral {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;

    private Spinner spinnerTipo, spinnerMarca, spinnerModelo;
    private EditText editTextAnio, editTextColor;
    private MaterialButton btnGuardar;

    private UsuarioDao usuarioDAO;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vehiculo);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Inicializar vistas
        spinnerTipo = findViewById(R.id.spinnerTipo);
        spinnerMarca = findViewById(R.id.spinnerMarca);
        spinnerModelo = findViewById(R.id.spinnerModelo);
        editTextAnio = findViewById(R.id.editTextAnio);
        editTextColor = findViewById(R.id.editTextColor);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Cargar datos para spinnerTipo y spinnerMarca
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this,
                R.array.tipos_vehiculo, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);

        ArrayAdapter<CharSequence> adapterMarca = ArrayAdapter.createFromResource(this,
                R.array.marcas_vehiculo, android.R.layout.simple_spinner_item);
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarca.setAdapter(adapterMarca);

        // Listener para cambiar spinnerModelo según marca seleccionada
        spinnerMarca.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                cargarModelosSegunMarca(position);
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });

        btnGuardar.setOnClickListener(v -> {
            String tipo = spinnerTipo.getSelectedItem().toString();
            String marca = spinnerMarca.getSelectedItem().toString();
            String modelo = spinnerModelo.getSelectedItem() != null ? spinnerModelo.getSelectedItem().toString() : "";
            String anioTexto = editTextAnio.getText().toString().trim();
            String color = editTextColor.getText().toString().trim();

            if (anioTexto.isEmpty() || color.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int anio = Integer.parseInt(anioTexto);

            userId = getIntent().getIntExtra("ID_USUARIO", -1);

            //ingresando datos a la base de datos
            Vehiculo nuevoVehiculo = new Vehiculo(userId, marca, modelo, tipo, anio, color);

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.vehiculoDao().insertar(nuevoVehiculo);

                runOnUiThread(() -> Toast.makeText(this, "Vehículo guardado en la base de datos", Toast.LENGTH_SHORT).show());
            }).start();
        });
    }

    private void cargarModelosSegunMarca(int posicionMarca) {
        int modelosArrayId;

        switch (posicionMarca) {
            case 0: // Marca 1
                modelosArrayId = R.array.modelos_marca_1;
                break;
            case 1: // Marca 2
                modelosArrayId = R.array.modelos_marca_2;
                break;
            case 2: // Marca 3
                modelosArrayId = R.array.modelos_marca_3;
                break;
            default:
                modelosArrayId = 0;
        }

        if (modelosArrayId != 0) {
            ArrayAdapter<CharSequence> adapterModelo = ArrayAdapter.createFromResource(this,
                    modelosArrayId, android.R.layout.simple_spinner_item);
            adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerModelo.setAdapter(adapterModelo);
        } else {
            spinnerModelo.setAdapter(null);
        }
    }
}
