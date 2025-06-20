package com.example.applogin.base_DAO;

import static androidx.room.ForeignKey.CASCADE;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index; // Import Index
import androidx.room.PrimaryKey;

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
        },
        indices = {
                @Index(value = {"id_usuario"}), // Index for id_usuario
                @Index(value = {"id_vehiculo"})  // Index for id_vehiculo
        })
public class Cita {
    @PrimaryKey(autoGenerate = true)
    public int id_cita;

    public int id_usuario;
    public int id_vehiculo;
    @NonNull
    public String fechaAgendada;
    public String observaciones;

    public Cita(int id_usuario, int id_vehiculo, @NonNull String fechaAgendada, String observaciones) {
        this.id_usuario = id_usuario;
        this.id_vehiculo = id_vehiculo;
        this.fechaAgendada = fechaAgendada;
        this.observaciones = observaciones;
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_cita) {
        this.id_cita = id_cita;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    @NonNull
    public String getFechaAgendada() {
        return fechaAgendada;
    }

    public void setFechaAgendada(@NonNull String fechaAgendada) {
        this.fechaAgendada = fechaAgendada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // ... Getters and Setters ...
}