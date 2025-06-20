package com.example.applogin.base_DAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.content.Context;

@Database(entities = {Rol.class, Usuario.class, Vehiculo.class, Cita.class, Historial.class},
        version = 2,
        exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    // DAOs
    public abstract RolDao rolDao();
    public abstract UsuarioDao usuarioDao();
    public abstract VehiculoDao vehiculoDao();
    public abstract CitaDao citaDao();
    public abstract HistorialDao historialDao();

    // Migración de versión 1 a 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Crear la tabla si no existe
            database.execSQL("CREATE TABLE IF NOT EXISTS `usuarios` " +
                    "(`id_usuario` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`nombre` TEXT, `password` TEXT)");
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "taller")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration() // solo en desarrollo
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}