package com.example.upapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.upapp.R
import com.example.upapp.components.AppDrawer
import com.example.upapp.components.AppTopBar
import com.example.upapp.navigation.Screen
import kotlinx.coroutines.launch

// Paleta de colores
val BtnLightGreen = Color(0xFFB1D4C0)
val TextDarkRed = Color(0xFFA62A2A)
val TextOliveGreen = Color(0xFF7B8B42)
val GreenBottomBar = Color(0xFFC4D6B0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgroAlertsScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Estados para los modales
    var showHuertoDialog by remember { mutableStateOf(false) }
    var showClimaDialog by remember { mutableStateOf(false) }
    var showLuciernagasDialog by remember { mutableStateOf(false) }
    var showVerMasDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onNavigateToNotifications = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.Notifications.route)
                },
                onNavigateToCalendar = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.CalendarEvents.route)
                },
                onNavigateToSettings = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToHelpComments = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.HelpComments.route)
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
                        /* Acción al presionar perfil */
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = GreenBottomBar,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(68.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Ir al Inicio",
                                tint = Color(0xFF047435),
                                modifier = Modifier.size(52.dp)
                            )
                        }
                    }
                }
            },
            containerColor = Color.White
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Icono de Alerta y Títulos
                Image(
                    painter = painterResource(id = R.drawable.alerta_roja),
                    contentDescription = "Icono de Alerta",
                    modifier = Modifier.size(72.dp)
                )

                Text(
                    text = "ALERTA DE RIESGOS",
                    color = TextDarkRed,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "AGROCLIMÁTICOS",
                    color = TextOliveGreen,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 1. Tarjeta Huerto escolar
                AgroCard(
                    imageRes = R.drawable.huerto,
                    onClick = { showHuertoDialog = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Tarjeta Cambios climáticos
                AgroCard(
                    imageRes = R.drawable.clima,
                    onClick = { showClimaDialog = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Tarjeta Luciérnagas
                AgroCard(
                    imageRes = R.drawable.luciernagas,
                    onClick = { showLuciernagasDialog = true }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Ver más (Abre el aviso de fuera de servicio)
                Button(
                    onClick = { showVerMasDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BtnLightGreen),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .width(165.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Ver más",
                        color = Color(0xFF07546A),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    // Modal Huerto escolar
    if (showHuertoDialog) {
        ImageModalDialog(
            imageRes = R.drawable.huerto_detalle, // Reemplaza por el nombre de tu archivo en drawable
            contentDescription = "Detalle de Huerto Escolar",
            onDismiss = { showHuertoDialog = false }
        )
    }

    // Modal Cambios climáticos
    if (showClimaDialog) {
        ImageModalDialog(
            imageRes = R.drawable.clima_detalle, // Reemplaza por el nombre de tu archivo en drawable
            contentDescription = "Detalle de Cambios Climáticos",
            onDismiss = { showClimaDialog = false }
        )
    }

    // Modal Luciérnagas (Fuera de servicio)
    if (showLuciernagasDialog) {
        InfoModalDialog(
            title = "Fuera de Servicio",
            message = "Esta opción estará disponible próximamente.",
            onDismiss = { showLuciernagasDialog = false }
        )
    }

    // Modal Ver más (Fuera de servicio)
    if (showVerMasDialog) {
        InfoModalDialog(
            title = "Fuera de Servicio",
            message = "Esta opción estará disponible próximamente.",
            onDismiss = { showVerMasDialog = false }
        )
    }
}

@Composable
fun AgroCard(imageRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
            .clip(RoundedCornerShape(50))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

// Modal optimizado para imágenes grandes que aprovecha todo el ancho de la pantalla
@Composable
fun ImageModalDialog(
    imageRes: Int,
    contentDescription: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = contentDescription,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Cerrar",
                        color = TextDarkRed,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Modal para alertas de texto (Fuera de servicio)
@Composable
fun InfoModalDialog(title: String, message: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = TextDarkRed,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Aceptar",
                        color = Color(0xFF047435),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}