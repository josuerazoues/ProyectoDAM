package com.example.applogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Vehiculo;

import java.util.List;

public class ListaVehiculosActivity extends MenuLateral {

    private RecyclerView recyclerVehiculos;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);

        configurarDrawer(R.id.toolbar, R.id.drawer_layout, R.id.navigation_view);

        recyclerVehiculos = findViewById(R.id.recyclerVehiculos);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this));

        userId = getIntent().getIntExtra("ID_USUARIO", -1);

        cargarVehiculos(userId);
    }

    private void cargarVehiculos(int idUsuario) {
        new Thread(() -> {
            List<Vehiculo> vehiculos = AppDatabase.getInstance(getApplicationContext())
                    .vehiculoDao()
                    .obtenerPorUsuario(idUsuario);

            runOnUiThread(() -> {
                if (vehiculos != null && !vehiculos.isEmpty()) {
                    recyclerVehiculos.setAdapter(new VehiculoAdapter(vehiculos));
                } else {
                    Toast.makeText(this, "No hay vehículos registrados", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private static class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> {
        private final List<Vehiculo> vehiculos;

        public VehiculoAdapter(List<Vehiculo> vehiculos) {
            this.vehiculos = vehiculos;
        }

        @NonNull
        @Override
        public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new VehiculoViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
            Vehiculo v = vehiculos.get(position);
            holder.titulo.setText(v.getMarca() + " " + v.getModelo());
            holder.descripcion.setText("Año: " + v.getAnio() + " | Color: " + v.getColor());
        }

        @Override
        public int getItemCount() {
            return vehiculos.size();
        }

        static class VehiculoViewHolder extends RecyclerView.ViewHolder {
            TextView titulo, descripcion;

            public VehiculoViewHolder(@NonNull View itemView) {
                super(itemView);
                titulo = itemView.findViewById(android.R.id.text1);
                descripcion = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
