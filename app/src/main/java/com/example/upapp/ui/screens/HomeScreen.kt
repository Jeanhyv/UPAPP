package com.example.upapp.ui.screens // Asegúrate de que este paquete coincida con el tuyo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.upapp.R
// Colores extraídos de la imagen original
val GreenBarColor = Color(0xFFC7D6BA)
val DarkGreenIcon = Color(0xFF1B5E20)
val BackgroundWhite = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpappScreen(
    // 🟢 AGREGAMOS LOS PARÁMETROS DE NAVEGACIÓN AQUÍ
    onNavigateToRadio: () -> Unit = {},
    onNavigateToMap: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_upapp),
                        contentDescription = "Logo de UPAPP",
                        modifier = Modifier
                            .width(100.dp)
                            .height(40.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Acción del menú de hamburguesa */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = DarkGreenIcon,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Acción al tocar el perfil */ },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.perfil_avatar),
                            contentDescription = "Perfil",
                            modifier = Modifier
                                .size(40.dp) // 🟢 Ajustado a 40dp para que quepa en la barra
                                .clip(CircleShape), // 🟢 Hace que la foto sea redonda
                            contentScale = ContentScale.Crop // 🟢 Evita que la foto se estire
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = GreenBarColor
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = GreenBarColor,
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { /* Acción de inicio */ }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = DarkGreenIcon,
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
                .background(BackgroundWhite)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            BannerSection()
            Spacer(modifier = Modifier.height(24.dp))

            // 🟢 CONECTAMOS LAS ACCIONES A LA CUADRÍCULA
            GridMenuSection(
                onRadioClick = onNavigateToRadio,
                onMapClick = onNavigateToMap
            )

            Spacer(modifier = Modifier.height(32.dp))
            ActionButtonsSection()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
@Composable
fun BannerSection() {
    // Usamos LazyRow para simular el carrusel donde se asoman las imágenes de los lados
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Elemento 1 (Imagen izquierda cortada)
        item {
            // 🟢 AQUÍ VA LA IMAGEN DEL CARRUSEL (Izquierda)
            Box(modifier = Modifier.width(40.dp).fillMaxHeight().background(Color.Gray))
        }

        // Elemento 2 (Banner central principal)
        item {
            // 🟢 AQUÍ VA EL BANNER CENTRAL (Alerta de riesgos agro climatológicos)
            // Reemplaza el Box por: Image(painter = painterResource(id = R.drawable.banner_alerta), contentScale = ContentScale.Crop, ...)
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFF6F0E6))
            ) {
                Text("BANNER CENTRAL", modifier = Modifier.align(Alignment.Center))
            }
        }

        // Elemento 3 (Imagen derecha cortada)
        item {
            // 🟢 AQUÍ VA LA IMAGEN DEL CARRUSEL (Derecha)
            Box(modifier = Modifier.width(40.dp).fillMaxHeight().background(Color.Gray))
        }
    }
}

@Composable
fun GridMenuSection(
    onRadioClick: () -> Unit, // 🟢 NUEVO: Recibe la acción para el radio
    onMapClick: () -> Unit    // 🟢 NUEVO: Recibe la acción para el mapa
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
                onClick = { /* Acción futura para alertas */ }
            )
            GridItem(
                color = Color(0xFFF1A882),
                iconResId = R.drawable.brum_brum,
                description = "Vehículo",
                onClick = { /* Acción futura para vehículo */ }
            )
            GridItem(
                color = Color(0xFFE7C1C1),
                iconResId = R.drawable.calendar,
                description = "Calendario",
                onClick = { /* Acción futura para calendario */ }
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
                onClick = onMapClick // 🟢 AQUÍ EJECUTA LA ACCIÓN DEL MAPA
            )
            GridItem(
                color = Color(0xFFA6C9B7),
                iconResId = R.drawable.documents,
                description = "Documentos",
                onClick = { /* Acción futura para documentos */ }
            )
            GridItem(
                color = Color(0xFFB9CEDB),
                iconResId = R.drawable.radio,
                description = "Radio",
                onClick = onRadioClick // 🟢 AQUÍ EJECUTA LA ACCIÓN DEL RADIO
            )
        }
    }
}

// 🟢 ESTA ES LA PLANTILLA ACTUALIZADA (Reemplaza la que tienes más abajo)
@Composable
fun GridItem(
    color: Color,
    iconResId: Int,
    description: String,
    onClick: () -> Unit // 🟢 NUEVO: Parámetro para recibir la acción de click
) {
    Box(
        modifier = Modifier
            .size(80.dp) // Tamaño del cuadro de color de fondo
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .clickable { onClick() }, // 🟢 NUEVO: Hace que el cuadro sea un botón tocable
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = description,
            // 🟢 AJUSTE DE TAMAÑO: Cambia este 48.dp por un número mayor (ej. 56.dp o 64.dp) si quieres el icono más grande
            modifier = Modifier.size(48.dp)
        )
    }
}
@Composable
fun GridItem(color: Color) {
    // Reemplazar el interior del Box con un Image(...) o Icon(...) de tus recursos
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text("Icon", color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun ActionButtonsSection() {
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
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6C9B7)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Ver más",
                color = Color(0xFF2C4A3B), // Color de texto oscuro
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = { /* Acción Eventos */ },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC7D7E0)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Eventos",
                color = Color(0xFF2C4A3B), // Color de texto oscuro
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}