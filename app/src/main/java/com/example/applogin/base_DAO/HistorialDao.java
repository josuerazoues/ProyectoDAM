package com.example.applogin.base_DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistorialDao {
    @Insert
    void insertar(Historial historial);

    @Query("SELECT * FROM historial")
    List<Historial> obtenerTodos();

    @Query("SELECT * FROM historial WHERE id_historial = :id")
    Historial obtenerPorId(int id);

    @Query("SELECT * FROM historial WHERE id_usuario = :idUsuario")
    List<Historial> obtenerPorUsuario(int idUsuario);

    @Query("SELECT * FROM historial WHERE id_cita = :idCita")
    List<Historial> obtenerPorCita(int idCita);

    @Query("SELECT * FROM historial WHERE estado = :estado")
    List<Historial> obtenerPorEstado(String estado);

    @Update
    void actualizar(Historial historial);

    @Delete
    void eliminar(Historial historial);
}