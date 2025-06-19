package com.example.applogin;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, Rol.class, Vehiculo.class, Cita.class, Historial.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UsuarioDao usuarioDao();
    // Puedes agregar más DAOs aquí: rolDao(), citaDao(), etc.

    public static AppDatabase getInstance(RegistroClienteActivity context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "mantenimiento_db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() // solo para pruebas
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
