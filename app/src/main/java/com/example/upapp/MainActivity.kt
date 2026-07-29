package com.example.upapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.upapp.screens.LoginScreen
import com.example.upapp.ui.theme.UPAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UPAPPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginSuccess = {
                            Toast.makeText(
                                this,
                                "¡Inicio de sesión exitoso! Redirigiendo...",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Aquí se llamará a la pantalla Home cuando tengamos el NavGraph
                        },
                        onForgotPasswordClick = {
                            Toast.makeText(
                                this,
                                "Redirigiendo a recuperación de contraseña...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}