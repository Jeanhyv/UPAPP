package com.example.upapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.upapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onBackClick: () -> Unit = {}
) {
    var showFullImageDialog by remember { mutableStateOf(false) }

    // Colores del tema institucional
    val headerFooterBg = Color(0xFFC7DCB8) // Verde claro institucional
    val darkGreen = Color(0xFF1B7339)      // Verde oscuro
    val dotMagenta = Color(0xFFD81B60)    // Punto magenta sobre la Å
    val crimsonRed = Color(0xFFC62828)    // Rojo para el texto inferior

    Scaffold(
        topBar = {
            // Encabezado con Menú + Logo UPÅPP + Avatar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerFooterBg)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = darkGreen,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { /* Abrir menú si aplica */ }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Logo UPÅPP
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "UP",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = darkGreen
                        )
                        Box(contentAlignment = Alignment.TopCenter) {
                            Text(
                                text = "A",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = darkGreen
                            )
                            Box(
                                modifier = Modifier
                                    .offset(y = (-2).dp)
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(dotMagenta)
                            )
                        }
                        Text(
                            text = "PP",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF707070)
                        )
                    }
                }

                // Avatar de Perfil
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3E1A3)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo1),
                        contentDescription = "Perfil",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        },
        bottomBar = {
            // Barra inferior verde con botón de Inicio
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerFooterBg)
                    .navigationBarsPadding()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = darkGreen,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- ICONO MAPA / PIN DE UBICACIÓN ---
            Box(
                modifier = Modifier
                    .size(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = Color(0xFF81C784),
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- TÍTULO PRINCIPAL ---
            Text(
                text = "CROQUIS 2026",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- TARJETA VISTA PREVIA DEL CROQUIS ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showFullImageDialog = true },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.croquis),
                    contentDescription = "Croquis de Espacios Académicos",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // --- TEXTO / ENLACE DE ABRIR MAPA ---
            Text(
                text = "Croquis UPA 2026",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = crimsonRed,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { showFullImageDialog = true }
            )
        }
    }

    // --- DIÁLOGO PANTALLA COMPLETA CON ZOOM Y DESPLAZAMIENTO ---
    if (showFullImageDialog) {
        Dialog(
            onDismissRequest = { showFullImageDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            var offsetX by remember { mutableFloatStateOf(0f) }
            var offsetY by remember { mutableFloatStateOf(0f) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.92f))
            ) {
                // Botón Cerrar
                IconButton(
                    onClick = { showFullImageDialog = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .statusBarsPadding()
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.White
                    )
                }

                // Imagen interactiva (Pinch-to-zoom)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 5f)
                                if (scale > 1f) {
                                    offsetX += pan.x
                                    offsetY += pan.y
                                } else {
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.croquis),
                        contentDescription = "Croquis Completo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY
                            )
                    )
                }
            }
        }
    }
}