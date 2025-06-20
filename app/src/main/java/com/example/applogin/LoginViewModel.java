package com.example.applogin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.applogin.base_DAO.AppDatabase;
import com.example.applogin.base_DAO.Usuario;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<Usuario> usuarioResult = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final AppDatabase db;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
    }

    public LiveData<Usuario> getUsuarioResult() {
        return usuarioResult;
    }

    public LiveData<String> getErrorMessage() {
        return error;
    }

    public void loginUser(String username, String password) {
        new Thread(() -> {
            try {
                Usuario usuario = db.usuarioDao().obtenerPorNombre(username);

                if (usuario == null) {
                    error.postValue("Usuario no encontrado");
                } else if (!usuario.getPassword().equals(password)) {
                    error.postValue("Contraseña incorrecta");
                } else {
                    usuarioResult.postValue(usuario);
                }
            } catch (Exception e) {
                error.postValue("Error al iniciar sesión: " + e.getLocalizedMessage());
            }
        }).start();
    }
}
