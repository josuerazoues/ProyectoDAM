package com.example.applogin.base_DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VehiculoDao {
    @Insert
    void insertar(Vehiculo vehiculo);

    @Query("SELECT * FROM vehiculos")
    List<Vehiculo> obtenerTodos();

    @Query("SELECT * FROM vehiculos WHERE id_vehiculo = :id")
    Vehiculo obtenerPorId(int id);

    @Query("SELECT * FROM vehiculos WHERE id_usuario = :idUsuario")
    List<Vehiculo> obtenerPorUsuario(int idUsuario);

    @Query("SELECT * FROM vehiculos WHERE marca LIKE :marca")
    List<Vehiculo> buscarPorMarca(String marca);

    @Update
    void actualizar(Vehiculo vehiculo);

    @Delete
    void eliminar(Vehiculo vehiculo);
}