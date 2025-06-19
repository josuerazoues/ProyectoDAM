package com.example.applogin;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "vehiculos",
        foreignKeys = @ForeignKey(entity = Usuario.class,
                parentColumns = "id_usuario",
                childColumns = "id_usuario",
                onDelete = CASCADE))
public class Vehiculo {
    public int id_vehiculo;

    public int id_usuario;

    @NonNull
    public String tipo;
    public String marca;
    public String modelo;
    public int anio;
    public String color;
}

