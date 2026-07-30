package com.example.upapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
// Colores del Drawer
val DrawerBgColor = Color(0xFFC7D6BA)     // Verde claro de fondo
val DrawerDarkGreen = Color(0xFF103A4B)   // Título "Mi cuenta" y texto de opciones
val DrawerIconGreen = Color(0xFF5A8E40)   // Verde de los iconos
val DotMagenta = Color(0xFFD81B60)

@Composable
fun CustomAppDrawer(
    onNavigateToCalendar: () -> Unit = {},
    onCloseDrawer: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    ModalDrawerSheet(
        drawerContainerColor = DrawerBgColor,
        drawerShape = RoundedCornerShape(topEnd = 0.dp, bottomEnd = 0.dp),
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // 1. BARRA DE BÚSQUEDA
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar ...", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF3EFE0),
                    unfocusedContainerColor = Color(0xFFF3EFE0),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 2. TÍTULO "Mi cuenta"
            Text(
                text = "Mi cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DrawerDarkGreen,
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
            )

            // 3. OPCIONES DEL MENÚ
            DrawerMenuItem(
                icon = Icons.Default.Notifications,
                text = "Notificaciones",
                iconTint = Color(0xFFD81B60), // Notificación roja/magenta
                onClick = { /* Próximamente */ }
            )

            // 🟢 CALENDARIO (ESTE SÍ NAVEGA)
            DrawerMenuItem(
                icon = Icons.Default.DateRange,
                text = "Calendario",
                iconTint = DrawerIconGreen,
                onClick = {
                    onCloseDrawer()
                    onNavigateToCalendar()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.Settings,
                text = "Configuración",
                iconTint = DrawerIconGreen,
                onClick = { /* Próximamente */ }
            )

            DrawerMenuItem(
                icon = Icons.Default.HelpOutline,
                text = "Ayuda y comentarios",
                iconTint = DrawerIconGreen,
                onClick = { /* Próximamente */ }
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 4. LÍNEAS DECORATIVAS VERDES
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF438A5E))
                )
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF438A5E))
                )
                Box(
                    modifier = Modifier
                        .width(115.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF438A5E))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. LOGO UPÅPP EN LA PARTE INFERIOR
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "UP",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                Box(contentAlignment = Alignment.TopCenter) {
                    Text(
                        text = "A",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = (-2).dp)
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(DotMagenta)
                    )
                }
                Text(
                    text = "PP",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF707070)
                )
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    text: String,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconTint,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = DrawerDarkGreen
        )
    }
}