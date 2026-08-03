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
import com.example.upapp.ui.screens.AgroAlertsScreen
import com.example.upapp.ui.screens.CalendarEventsScreen
import com.example.upapp.ui.screens.DocumentsScreen
import com.example.upapp.ui.screens.HelpCommentsScreen
import com.example.upapp.ui.screens.HomeScreen
import com.example.upapp.ui.screens.LoginScreen
import com.example.upapp.ui.screens.MapScreen
import com.example.upapp.ui.screens.NotificationsScreen
import com.example.upapp.ui.screens.RadioScreen
import com.example.upapp.ui.screens.RegisterScreen // 👈 ASEGÚRATE DE TENER ESTO
import com.example.upapp.ui.screens.SettingsScreen
import com.example.upapp.ui.screens.SupportScreen   // 👈 ASEGÚRATE DE TENER ESTO
import com.example.upapp.ui.screens.VehiclePassScreen
import com.example.upapp.ui.theme.DarkGray
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route,
    isDarkTheme: Boolean = false,
    onThemeChange: (Boolean) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
// Pantalla de Login
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.Support.route)
                },
                onRegisterClick = { // 👈 Navega a la pantalla de Registro
                    navController.navigate(Screen.Register.route)
                }
            )
        }

// Pantalla de Registro
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBackToLoginClick = {
                    navController.popBackStack()
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

        // 3. Pantalla de Inicio / Dashboard
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToMap = {
                    navController.navigate(Screen.Map.route)
                },
                onNavigateToRadio = {
                    navController.navigate(Screen.Radio.route)
                },
                onNavigateToCalendar = {
                    navController.navigate(Screen.CalendarEvents.route)
                },
                onNavigateToDocuments = {
                    navController.navigate(Screen.Documents.route)
                },
                onNavigateToAgroAlerts = {
                    navController.navigate(Screen.AgroAlerts.route)
                },
                onNavigateToVehiclePass = {
                    navController.navigate(Screen.VehiclePass.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToHelpComments = {
                    navController.navigate(Screen.HelpComments.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                }
            )
        }

        // 4. Credencial Digital
        composable(route = Screen.ProfileCredential.route) {
            PlaceholderScreen(title = "Credencial Estudiantil Digital")
        }

        // 5. Tarjetón Vehicular
        composable(route = Screen.VehiclePass.route) {
            VehiclePassScreen(
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 6. Calendario y Eventos
        composable(route = Screen.CalendarEvents.route) {
            CalendarEventsScreen(navController = navController)
        }

        // 7. Alertas Agroclimáticas
        composable(route = Screen.AgroAlerts.route) {
            AgroAlertsScreen(navController = navController)
        }

        // 8. Mapa / Croquis
        composable(route = Screen.Map.route) {
            MapScreen(navController = navController)
        }

        // 9. Documentos
        composable(route = Screen.Documents.route) {
            DocumentsScreen(navController = navController)
        }

        // 10. UPA Radio
        composable(route = Screen.Radio.route) {
            RadioScreen(
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 11. Ayuda y Comentarios
        composable(route = Screen.HelpComments.route) {
            HelpCommentsScreen(navController = navController)
        }

        // 12. Notificaciones (🟢 Se eliminó la duplicada)
        composable(route = Screen.Notifications.route) {
            NotificationsScreen(
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 13. Ajustes / Configuración
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange, // 🟢 Conectado al parámetro de NavGraph
                navController = navController,
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