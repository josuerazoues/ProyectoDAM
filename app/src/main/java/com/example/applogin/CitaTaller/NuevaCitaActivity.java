package com.example.applogin.CitaTaller;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applogin.R;
import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Cita;
import com.example.applogin.base_DAO.Vehiculo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevaCitaActivity extends AppCompatActivity {

    private Spinner spinnerVehiculos;
    private EditText editFecha, editObservaciones;
    private Button btnGuardar;
    private AppDatabase db;
    private List<Vehiculo> vehiculos;
    private Cita cita;
    private int idUsuario = 1; // Reemplazar con el ID del usuario actual que inicia sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cita);

        db = AppDatabase.getInstance(getApplicationContext());

        spinnerVehiculos = findViewById(R.id.spinnerVehiculos);
        editFecha = findViewById(R.id.editFecha);
        editObservaciones = findViewById(R.id.editObservaciones);
        btnGuardar = findViewById(R.id.btnGuardarCita);

        cargarVehiculos();

        editFecha.setOnClickListener(v -> mostrarDatePicker());

        btnGuardar.setOnClickListener(v -> guardarCita());
    }

    private void cargarVehiculos() {
        vehiculos = db.vehiculoDao().obtenerPorUsuario(idUsuario);
        List<String> nombres = new ArrayList<>();

        for (Vehiculo v : vehiculos) {
            nombres.add(v.getMarca() + " - " + v.getModelo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, nombres);
        spinnerVehiculos.setAdapter(adapter);
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fecha = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    editFecha.setText(fecha);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void guardarCita() {
        String fecha = editFecha.getText().toString().trim();
        String observaciones = editObservaciones.getText().toString().trim();

        if (fecha.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        int vehiculoId = vehiculos.get(spinnerVehiculos.getSelectedItemPosition()).getId_vehiculo();

        cita.setId_usuario(idUsuario);
        cita.setId_vehiculo(vehiculoId);
        cita.setFechaAgendada(fecha);
        cita.setObservaciones(observaciones);

        db.citaDao().insertar(cita);

        Toast.makeText(this, "Cita guardada con éxito", Toast.LENGTH_SHORT).show();
        finish(); // cerrar la pantalla
    }
}
