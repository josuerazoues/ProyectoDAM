package com.example.applogin;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "citas",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "id_usuario",
                        childColumns = "id_usuario",
                        onDelete = CASCADE),
                @ForeignKey(entity = Vehiculo.class,
                        parentColumns = "id_vehiculo",
                        childColumns = "id_vehiculo",
                        onDelete = CASCADE)
        })
public class Cita {
    public int id_cita;

    public int id_usuario;
    public int id_vehiculo;

    @NonNull
    public String fechaAgendada;
    public String observaciones;
}
