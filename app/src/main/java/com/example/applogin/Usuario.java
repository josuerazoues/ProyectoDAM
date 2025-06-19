package com.example.applogin;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "usuarios",
        foreignKeys = @ForeignKey(entity = Rol.class,
                parentColumns = "id_rol",
                childColumns = "id_rol",
                onDelete = CASCADE))
public class Usuario {
    public int id_usuario;

    @NonNull
    public String nombre;
    @NonNull public String correo;
    @NonNull public String telefono;
    public int id_rol;
}
