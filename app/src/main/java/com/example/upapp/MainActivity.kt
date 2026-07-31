package com.example.upapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.upapp.navigation.NavGraph
import com.example.upapp.ui.theme.UPAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa la pantalla de carga (Splash Screen)
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            // 1. Creamos la variable que "recuerda" el estado del tema
            var darkThemeActive by remember { mutableStateOf(false) }

            // 2. Le pasamos esa variable a tu Tema principal
            UPAPPTheme(darkTheme = darkThemeActive) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // 3. Le pasamos el estado al NavGraph
                    NavGraph(
                        navController = navController,
                        isDarkTheme = darkThemeActive,
                        onThemeChange = { nuevoEstado ->
                            darkThemeActive = nuevoEstado
                        }
                    )
                }
            }
        }
    }
}