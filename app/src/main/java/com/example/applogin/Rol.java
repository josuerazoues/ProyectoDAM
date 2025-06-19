package com.example.applogin;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "roles")
public class Rol {
    public int id_rol;

    @NonNull
    public String rol;
}
