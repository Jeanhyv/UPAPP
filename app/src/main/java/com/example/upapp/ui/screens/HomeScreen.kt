package com.example.upapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun UpappScreen() {
    Scaffold(
        topBar = {
            // Usamos CenterAlignedTopAppBar para que el título (Logo) quede exactamente en medio
            CenterAlignedTopAppBar(
                title = {
                    // 🟢 AQUÍ VA EL LOGO PRINCIPAL (UPAPP) EN EL CENTRO
                    // Reemplaza el Box con: Image(painter = painterResource(id = R.drawable.tu_logo_upapp), ...)
                    Image(
                        painter = painterResource(id = R.drawable.home), // Sin la extensión .png
                        contentDescription = "Descripción de la imagen", // Obligatorio (puede ser null)
                        modifier = Modifier.fillMaxSize() // Opcional: para que se ajuste al tamaño del Box
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
                    // 🟢 AQUÍ VA LA FOTO DE PERFIL A LA DERECHA (Avatar de la chica)
                    // Reemplaza el Box con: Image(painter = painterResource(id = R.drawable.tu_foto_perfil), ...)
                    IconButton(
                        onClick = { /* Acción al tocar el perfil */ },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF3D28E)) // Fondo temporal del avatar
                        ) {
                            Text("FOTO", fontSize = 10.sp, modifier = Modifier.align(Alignment.Center))
                        }
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
            GridMenuSection()
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
fun GridMenuSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        // Fila 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🟢 AQUÍ VA EL LOGO DEL TRIÁNGULO DE ADVERTENCIA
            GridItem(color = Color(0xFFD67D7D)) // Rojo/Rosa
            // 🟢 AQUÍ VA EL LOGO DEL COCHE
            GridItem(color = Color(0xFFF1A882)) // Naranja
            // 🟢 AQUÍ VA EL LOGO DEL CALENDARIO
            GridItem(color = Color(0xFFE7C1C1)) // Rosa claro
        }

        // Fila 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🟢 AQUÍ VA EL LOGO DEL MAPA/UBICACIÓN
            GridItem(color = Color(0xFFF1D18A)) // Amarillo
            // 🟢 AQUÍ VA EL LOGO DE LOS DOCUMENTOS
            GridItem(color = Color(0xFFA6C9B7)) // Verde agua
            // 🟢 AQUÍ VA EL LOGO DEL MICRÓFONO
            GridItem(color = Color(0xFFB9CEDB)) // Azul claro
        }
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