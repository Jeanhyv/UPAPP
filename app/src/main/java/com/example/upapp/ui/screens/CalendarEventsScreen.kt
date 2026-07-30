package com.example.upapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.material.icons.outlined.LocationOn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.upapp.R
import java.util.Calendar

// Modelo de datos para cada evento compatible con API 24
data class EventItem(
    val year: Int,
    val month: Int, // 1 = Enero, 6 = Junio, etc.
    val day: Int,
    val dayNumber: String,
    val monthText: String,
    val title: String,
    val location: String,
    val time: String,
    val cardBgColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarEventsScreen(
    onBackClick: () -> Unit = {}
) {
    var showFullImageDialog by remember { mutableStateOf(false) }

    // Colores del tema
    val headerFooterBg = Color(0xFFC7DCB8)
    val darkGreen = Color(0xFF1B7339)
    val titleDarkColor = Color(0xFF103A4B)
    val dotMagenta = Color(0xFFD81B60)

    // Lista de eventos
    val allEvents = remember {
        listOf(
            EventItem(
                year = 2026, month = 6, day = 15,
                dayNumber = "15", monthText = "Jun",
                title = "Platicas de salud",
                location = "Conservatorio",
                time = "10:00 AM - 12:00 PM",
                cardBgColor = Color(0xFFD0E1E9)
            ),
            EventItem(
                year = 2026, month = 6, day = 22,
                dayNumber = "22", monthText = "Jun",
                title = "Quermes",
                location = "Explanada",
                time = "10:00 AM - 2:00 PM",
                cardBgColor = Color(0xFFF3E1A3)
            ),
            EventItem(
                year = 2026, month = 7, day = 5,
                dayNumber = "05", monthText = "Jul",
                title = "Limpieza de areas verdes",
                location = "Explanada",
                time = "12:00 PM - 2:00 PM",
                cardBgColor = Color(0xFFEAB8C5)
            ),
            EventItem(
                year = 2026, month = 8, day = 10,
                dayNumber = "10", monthText = "Ago",
                title = "Inicio de Cuatrimestre",
                location = "Campus Central",
                time = "08:00 AM",
                cardBgColor = Color(0xFFD0E1E9)
            )
        )
    }

    // Lógica con Calendar compatible desde API 1
    val nextEvent = remember {
        val today = Calendar.getInstance()
        val currentYear = today.get(Calendar.YEAR)
        val currentMonth = today.get(Calendar.MONTH) + 1
        val currentDay = today.get(Calendar.DAY_OF_MONTH)

        allEvents.firstOrNull { event ->
            if (event.year > currentYear) true
            else if (event.year == currentYear && event.month > currentMonth) true
            else if (event.year == currentYear && event.month == currentMonth && event.day >= currentDay) true
            else false
        } ?: allEvents.last()
    }

    Scaffold(
        topBar = {
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
                            .clickable { }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "UP", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = darkGreen)
                        Box(contentAlignment = Alignment.TopCenter) {
                            Text(text = "A", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = darkGreen)
                            Box(
                                modifier = Modifier
                                    .offset(y = (-2).dp)
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(dotMagenta)
                            )
                        }
                        Text(text = "PP", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF707070))
                    }
                }

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
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFFEAB8C5),
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Pròximos eventos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleDarkColor
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            SingleEventCardItem(event = nextEvent, titleColor = titleDarkColor)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 3.dp, color = darkGreen)
                Text(
                    text = "Calendario",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleDarkColor,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 3.dp, color = darkGreen)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clickable { showFullImageDialog = true },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calendario),
                    contentDescription = "Vista previa del Calendario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { showFullImageDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5D8C5))
                ) {
                    Text(
                        text = "Ver más",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = titleDarkColor
                    )
                }

                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(0.8f)
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBDCE3))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }

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
                    .background(Color.Black.copy(alpha = 0.9f))
            ) {
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
                        painter = painterResource(id = R.drawable.calendario),
                        contentDescription = "Calendario Escolar Completo",
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

@Composable
private fun SingleEventCardItem(event: EventItem, titleColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = event.cardBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEFE6DD))
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = event.dayNumber,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = titleColor
                    )
                    Text(
                        text = event.monthText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = titleColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = titleColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = event.location,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = titleColor
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = titleColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = event.time,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = titleColor
                    )
                }
            }
        }
    }
}