package com.example.applogin.CitaTaller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import com.example.applogin.base_DAO.Cita;

import com.example.applogin.MenuLateral;
import com.example.applogin.R;
import com.example.applogin.base_DAO.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class CitasTaller extends MenuLateral {

    private AppDatabase db;
    private List<Cita> listaCitas;
    private SearchView searchView;
    private FloatingActionButton btnNuevaCita;
    private LinearLayout layoutCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_taller);

        // Toolbar y drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.nav_view);

        // Inicializar BD y vistas
        db = AppDatabase.getInstance(getApplicationContext());
        searchView = findViewById(R.id.searchView);
        btnNuevaCita = findViewById(R.id.btnNuevaCita);

        // Obtener y mostrar todas las citas
        listaCitas = db.citaDao().obtenerTodos();
        mostrarCitas(listaCitas);

        // Buscar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrar(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrar(newText);
                return true;
            }
        });

        btnNuevaCita.setOnClickListener(v -> {
            startActivity(new Intent(CitasTaller.this, NuevaCitaActivity.class));
        });
    }

    private void mostrarCitas(List<Cita> citas) {
        layoutCitas.removeAllViews();

        for (Cita c : citas) {
            TextView tv = new TextView(this);
            tv.setText("Vehículo ID: " + c.getId_vehiculo() + "\nFecha: " + c.getFechaAgendada() +
                    "\nObservaciones: " + c.getObservaciones());
            tv.setPadding(24, 24, 24, 24);
            tv.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 24);
            tv.setLayoutParams(params);
            layoutCitas.addView(tv);
        }
    }

    private void filtrar(String texto) {
        List<Cita> filtradas = new ArrayList<>();
        for (Cita c : listaCitas) {
            if ((c.getFechaAgendada() != null && c.getFechaAgendada().contains(texto)) ||
                    (c.getObservaciones() != null &&
                            c.getObservaciones().toLowerCase().contains(texto.toLowerCase()))) {
                filtradas.add(c);
            }
        }
        mostrarCitas(filtradas);
    }
}
