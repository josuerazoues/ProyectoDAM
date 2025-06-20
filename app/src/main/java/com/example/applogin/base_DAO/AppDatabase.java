package com.example.applogin.base_DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {
                Rol.class,
                Usuario.class,
                Vehiculo.class,
                Cita.class,
                Historial.class
        },
        version = 3, // Versión incrementada para evitar conflicto de esquema
        exportSchema = true
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // DAOs
    public abstract RolDao rolDao();
    public abstract UsuarioDao usuarioDao();
    public abstract VehiculoDao vehiculoDao();
    public abstract CitaDao citaDao();
    public abstract HistorialDao historialDao();

    // Ejemplo de migración (no necesaria si usas fallbackToDestructiveMigration)
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Este bloque puede quedar vacío si vas a destruir la base y recrearla
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "taller"
                    )
                    // Solo para desarrollo: elimina y recrea la base si hay cambios
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
