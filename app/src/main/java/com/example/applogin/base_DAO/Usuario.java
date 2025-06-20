package com.example.applogin.base_DAO;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios",
        foreignKeys = @ForeignKey(
                entity = Rol.class,
                parentColumns = "id_rol",
                childColumns = "id_rol",
                onDelete = CASCADE),
        indices = {@Index(value = "id_rol"), @Index(value = "nombre", unique = true), @Index(value = "correo", unique = true)}
)
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_usuario")
    private int id_usuario;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "correo")
    private String correo;

    @NonNull
    @ColumnInfo(name = "telefono")
    private String telefono;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "id_rol")
    private int id_rol;

    // Constructor principal para Room
    public Usuario(int id_usuario, @NonNull String nombre, @NonNull String correo, @NonNull String telefono, @NonNull String password, int id_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.id_rol = id_rol;
    }

    // Constructor personalizado ignorado por Room (útil para inserciones manuales)
    @Ignore
    public Usuario(@NonNull String nombre, @NonNull String correo, @NonNull String telefono, @NonNull String password, int id_rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.id_rol = id_rol;
    }

    // Constructor vacío obligatorio para Room
    public Usuario() {}

    // Getters y setters
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

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}
