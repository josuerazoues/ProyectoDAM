package com.example.applogin.base_DAO;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
        },
        indices = {
                @Index(value = {"id_usuario"}),
                @Index(value = {"id_cita"}),
                @Index(value = {"id_vehiculo"})
        })
public class Historial {
    @PrimaryKey(autoGenerate = true)
    private int id_historial;

    private int id_usuario;
    private int id_cita;
    private int id_vehiculo;

    @NonNull
    private String estado;

    @NonNull
    private String fechaActualizacion;

    private String observacionesMecanico;

    // Constructor
    public Historial(int id_usuario, int id_cita, int id_vehiculo,
                     @NonNull String estado, @NonNull String fechaActualizacion,
                     String observacionesMecanico) {
        this.id_usuario = id_usuario;
        this.id_cita = id_cita;
        this.id_vehiculo = id_vehiculo;
        this.estado = estado;
        this.fechaActualizacion = fechaActualizacion;
        this.observacionesMecanico = observacionesMecanico;
    }

    // Getters y Setters
    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_cita) {
        this.id_cita = id_cita;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @NonNull
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(@NonNull String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getObservacionesMecanico() {
        return observacionesMecanico;
    }

    public void setObservacionesMecanico(String observacionesMecanico) {
        this.observacionesMecanico = observacionesMecanico;
    }
}