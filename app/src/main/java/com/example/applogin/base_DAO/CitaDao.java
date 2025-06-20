package com.example.applogin.base_DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CitaDao {
    @Insert
    void insertar(Cita cita);

    @Query("SELECT * FROM citas")
    List<Cita> obtenerTodos();

    @Query("SELECT * FROM citas WHERE id_cita = :id")
    Cita obtenerPorId(int id);

    @Query("SELECT * FROM citas WHERE id_usuario = :idUsuario")
    List<Cita> obtenerPorUsuario(int idUsuario);

    @Query("SELECT * FROM citas WHERE id_vehiculo = :idVehiculo")
    List<Cita> obtenerPorVehiculo(int idVehiculo);

    @Query("SELECT * FROM citas WHERE fechaAgendada BETWEEN :fechaInicio AND :fechaFin")
    List<Cita> obtenerPorRangoFechas(String fechaInicio, String fechaFin);

    @Update
    void actualizar(Cita cita);

    @Delete
    void eliminar(Cita cita);
}