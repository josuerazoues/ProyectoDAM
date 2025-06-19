package com.example.applogin;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void insertar(Usuario usuario);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();

    @Query("SELECT * FROM usuarios WHERE id_usuario = :id")
    Usuario obtenerPorId(int id);

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND telefono = :telefono")
    Usuario loginSimple(String correo, String telefono);

    @Update
    void actualizar(Usuario usuario);

    @Delete
    void eliminar(Usuario usuario);
}

