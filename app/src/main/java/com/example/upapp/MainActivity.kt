@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.upapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.upapp.screens.CalendarEventsScreen
import com.example.upapp.ui.theme.UPAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa la Splash Screen
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            UPAPPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Carga directamente la pantalla de Calendario y Eventos
                    CalendarEventsScreen()
                }
            }
        }
    }
}