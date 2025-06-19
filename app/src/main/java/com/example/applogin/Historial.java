package com.example.applogin;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "historial",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "id_usuario",
                        childColumns = "id_usuario",
                        onDelete = CASCADE),
                @ForeignKey(entity = Cita.class,
                        parentColumns = "id_cita",
                        childColumns = "id_cita",
                        onDelete = CASCADE),
                @ForeignKey(entity = Vehiculo.class,
                        parentColumns = "id_vehiculo",
                        childColumns = "id_vehiculo",
                        onDelete = CASCADE)
        })
public class Historial {
    public int id_historial;

    public int id_usuario;
    public int id_cita;
    public int id_vehiculo;

    @NonNull
    public String estado;
    public String fechaActualizacion;
    public String observacionesMecanico;
}
