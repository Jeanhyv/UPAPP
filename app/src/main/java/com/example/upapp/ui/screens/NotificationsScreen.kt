package com.example.upapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.upapp.components.AppDrawer
import com.example.upapp.components.AppTopBar
import com.example.upapp.navigation.Screen
import kotlinx.coroutines.launch

private val NotificationsBackground = Color(0xFFF7F4E8)
private val NotificationsGreen = Color(0xFF103A4B)
private val NotificationsPink = Color(0xFFD81B60)
private val GreenBottomBarNotifications = Color(0xFFC4D6B0)
private val DarkGreenHomeIcon = Color(0xFF047435)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController = rememberNavController(),
    onBackClick: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onNavigateToNotifications = {
                    scope.launch { drawerState.close() }
                },
                onNavigateToCalendar = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.CalendarEvents.route)
                },
                onNavigateToSettings = {
                    scope.launch { drawerState.close() }
                    // navController.navigate(Screen.Settings.route)
                },
                onNavigateToHelpComments = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.HelpComments.route)
                },
                onLogout = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // Limpia el historial para no regresar con "Atrás"
                    }
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    },
                    onProfileClick = {
                        /* Acción de perfil */
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = GreenBottomBarNotifications,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(68.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Regresar a Inicio",
                                tint = DarkGreenHomeIcon,
                                modifier = Modifier.size(52.dp)
                            )
                        }
                    }
                }
            },
            containerColor = NotificationsBackground
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = NotificationsPink,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Notificaciones",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = NotificationsGreen
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Aquí aparecerán tus avisos y mensajes.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}