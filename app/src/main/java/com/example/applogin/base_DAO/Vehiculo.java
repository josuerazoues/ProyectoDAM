package com.example.applogin.base_DAO;

import static androidx.room.ForeignKey.CASCADE;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
// Make sure you have this import if you intend to use indexes,
// though it's not strictly necessary for this specific fix.
// import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehiculos",
        foreignKeys = @ForeignKey(entity = Usuario.class,
                parentColumns = "id_usuario",
                childColumns = "id_usuario", // This now correctly refers to the idUsuario field
                onDelete = CASCADE))
public class Vehiculo {
    @PrimaryKey(autoGenerate = true)
    private int id_vehiculo;

    @ColumnInfo(name = "id_usuario") // This annotation ensures the column name is "id_usuario"
    private int id_usuario; // Field name is now camelCase

    private String tipo;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    // Removed: private int id_usuario;


    // Updated constructor to use idUsuario
    public Vehiculo(int idUsuario, @NonNull String marca, @NonNull String modelo, String tipo, int anio, String color) {
        this.id_usuario = idUsuario;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.anio = anio;
        this.color = color;
    }

    public Vehiculo() {

    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    // Updated getter for idUsuario
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @NonNull
    public String getMarca() {
        return marca;
    }

    public void setMarca(@NonNull String marca) {
        this.marca = marca;
    }

    @NonNull
    public String getModelo() {
        return modelo;
    }

    public void setModelo(@NonNull String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}