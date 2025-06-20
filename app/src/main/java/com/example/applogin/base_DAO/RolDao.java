package com.example.applogin.base_DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RolDao {
    @Insert
    void insertar(Rol rol);

    @Query("SELECT * FROM roles")
    List<Rol> obtenerTodos();

    @Query("SELECT * FROM roles WHERE id_rol = :id")
    Rol obtenerPorId(int id);

    @Update
    void actualizar(Rol rol);

    @Delete
    void eliminar(Rol rol);

    @Query("SELECT COUNT(*) FROM roles")
    int contarRoles();
}