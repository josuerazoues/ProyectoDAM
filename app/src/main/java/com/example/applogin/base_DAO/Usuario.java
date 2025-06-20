package com.example.applogin.base_DAO;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index; // Import Index
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios",
        foreignKeys = @ForeignKey(entity = Rol.class,
                parentColumns = "id_rol",
                childColumns = "id_rol",
                onDelete = CASCADE))
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id_usuario;

    @NonNull
    private String nombre;
    @NonNull
    private String correo;
    @NonNull
    private String telefono;
    @NonNull
    private  String password;
    @ColumnInfo(name = "id_rol")
    private int id_rol;

    public Usuario(int id_usuario, @NonNull String nombre, @NonNull String password, @NonNull String correo, @NonNull String telefono, int id_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.password = password;
        this.correo = correo;
        this.telefono = telefono;
        this.id_rol = id_rol;
    }


    public Usuario(String usuario, String email, String telefono, String password, int i) {
    }

    public Usuario() {

    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(@NonNull String correo) {
        this.correo = correo;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}