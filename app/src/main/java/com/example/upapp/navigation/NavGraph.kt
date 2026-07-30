package com.example.upapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.upapp.screens.LoginScreen
import com.example.upapp.screens.RadioScreen
import com.example.upapp.screens.SupportScreen
import com.example.upapp.ui.theme.DarkGray
import com.example.upapp.ui.screens.UpappScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 1. Pantalla de Login (Autenticación)
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.Support.route)
                }
            )
        }

        // 2. Pantalla de Soporte / Recuperación de contraseña
        composable(route = Screen.Support.route) {
            SupportScreen(
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // 3. Pantalla de Inicio / Dashboard (Integrante 2)
        composable(route = Screen.Home.route) {
            UpappScreen(
                onNavigateToMap = {
                    navController.navigate(Screen.Map.route)
                },
                onNavigateToRadio = {
                    navController.navigate(Screen.Radio.route)
                }
            )
        }

        // 4. Credencial Digital (Integrante 3)
        composable(route = Screen.ProfileCredential.route) {
            PlaceholderScreen(title = "Credencial Estudiantil Digital")
        }

        // 5. Tarjetón Vehicular (Integrante 3)
        composable(route = Screen.VehiclePass.route) {
            PlaceholderScreen(title = "Tarjetón Vehicular")
        }

        // 6. Calendario y Eventos (Integrante 2)
        composable(route = Screen.CalendarEvents.route) {
            PlaceholderScreen(title = "Calendario y Eventos")
        }

        // 7. Alertas Agroclimáticas (Integrante 4)
        composable(route = Screen.AgroAlerts.route) {
            PlaceholderScreen(title = "Alertas Agroclimáticas")
        }

        // 8. Mapa / Croquis (Integrante 4)
        composable(route = Screen.Map.route) {
            PlaceholderScreen(title = "Croquis del Campus")
        }

        // 9. Documentos (Integrante 4)
        composable(route = Screen.Documents.route) {
            PlaceholderScreen(title = "Documentos del Alumno")
        }

        // 10. UPA Radio (Integrante 2)
        composable(route = Screen.Radio.route) {
            RadioScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = DarkGray
        )
    }
}