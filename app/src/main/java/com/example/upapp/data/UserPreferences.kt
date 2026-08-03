package com.example.upapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Instancia única de DataStore ligada al contexto
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        // Llaves para guardar el usuario local
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_PASSWORD = stringPreferencesKey("user_password")
    }

    // 🟢 Cambiado a "suspend fun"
    suspend fun registerUser(email: String, pass: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_EMAIL] = email.trim().lowercase()
            preferences[KEY_USER_PASSWORD] = pass
        }
    }

    // Validar credenciales al iniciar sesión
    suspend fun validateLogin(email: String, pass: String): Boolean {
        val preferences = context.dataStore.data.first()
        val savedEmail = preferences[KEY_USER_EMAIL]
        val savedPassword = preferences[KEY_USER_PASSWORD]

        // Compara los datos guardados localmente con lo que ingresa el usuario
        return savedEmail == email.trim().lowercase() && savedPassword == pass
    }

    // Saber si existe una cuenta creada
    suspend fun hasUser(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[KEY_USER_EMAIL] != null
    }
}