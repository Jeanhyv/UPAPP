package com.example.upapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.R
import com.example.upapp.components.AppTopBar
import com.example.upapp.components.AppDrawer
import kotlinx.coroutines.launch

// 🟢 Mantenemos los colores para los botones o elementos que siempre deban ser de la marca
val GreenBarColor = Color(0xFFC7D6BA)
val DarkGreenIcon = Color(0xFF1B5E20)
// Ya no usamos BackgroundWhite, usaremos MaterialTheme.colorScheme.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToRadio: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToDocuments: () -> Unit = {},
    onNavigateToAgroAlerts: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToHelpComments: () -> Unit = {},
    onNavigateToVehiclePass: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onNavigateToNotifications = onNavigateToNotifications,
                onNavigateToCalendar = onNavigateToCalendar,
                onNavigateToSettings = onNavigateToSettings,
                onNavigateToHelpComments = onNavigateToHelpComments,
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
                    // 🟢 CAMBIO: Usamos surfaceVariant para que en modo claro sea grisáceo/blanco y en oscuro se vuelva gris oscuro/negro
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { /* Acción inicio */ }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Inicio",
                                // 🟢 CAMBIO: Adaptamos el icono al color de la superficie
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // 🟢 CAMBIO PRINCIPAL: Esto hace que el fondo pase de blanco a negro según el Switch
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                BannerSection()
                Spacer(modifier = Modifier.height(24.dp))

                GridMenuSection(
                    onRadioClick = onNavigateToRadio,
                    onMapClick = onNavigateToMap,
                    onCalendarClick = onNavigateToCalendar,
                    onDocumentsClick = onNavigateToDocuments,   // 🟢 Corregido
                    onAgroAlertsClick = onNavigateToAgroAlerts, // 🟢 Corregido
                    onVehicleClick = onNavigateToVehiclePass
                )

                Spacer(modifier = Modifier.height(32.dp))
                ActionButtonsSection(
                    onCalendarClick = onNavigateToCalendar
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun BannerSection() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .fillMaxHeight()
                    // 🟢 CAMBIO: Color dinámico para los banners laterales
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    // 🟢 CAMBIO: Color dinámico para el banner central
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "BANNER CENTRAL",
                    // 🟢 CAMBIO: El texto se vuelve blanco en modo oscuro
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .fillMaxHeight()
                    // 🟢 CAMBIO: Color dinámico
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}

@Composable
fun GridMenuSection(
    onRadioClick: () -> Unit,
    onMapClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    onAgroAlertsClick: () -> Unit,
    onVehicleClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        // Fila 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GridItem(
                color = Color(0xFFD67D7D),
                iconResId = R.drawable.warning,
                description = "Alertas",
                onClick = onAgroAlertsClick
            )
            GridItem(
                color = Color(0xFFF1A882),
                iconResId = R.drawable.brum_brum,
                description = "Vehículo",
                onClick = onVehicleClick
            )
            GridItem(
                color = Color(0xFFE7C1C1),
                iconResId = R.drawable.calendar,
                description = "Calendario",
                onClick = onCalendarClick
            )
        }

        // Fila 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GridItem(
                color = Color(0xFFF1D18A),
                iconResId = R.drawable.school_map,
                description = "Croquis",
                onClick = onMapClick
            )
            GridItem(
                color = Color(0xFFA6C9B7),
                iconResId = R.drawable.documents,
                description = "Documentos",
                onClick = onDocumentsClick
            )
            GridItem(
                color = Color(0xFFB9CEDB),
                iconResId = R.drawable.radio,
                description = "Radio",
                onClick = onRadioClick
            )
        }
    }
}

@Composable
fun GridItem(
    color: Color,
    iconResId: Int,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = description,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun ActionButtonsSection(
    onCalendarClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* Acción Ver más */ },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            // Los colores de estos botones se pueden mantener fijos (branding) ya que el texto oscuro contrasta bien
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6C9B7)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Ver más",
                color = Color(0xFF2C4A3B),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = onCalendarClick,
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC7D7E0)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Eventos",
                color = Color(0xFF2C4A3B),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}