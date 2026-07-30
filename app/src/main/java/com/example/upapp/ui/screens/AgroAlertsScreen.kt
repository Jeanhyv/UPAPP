package com.example.upapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.upapp.R

// Paleta de colores
val GreenTopBar = Color(0xFFC4D6B0)
val BtnLightGreen = Color(0xFFB1D4C0)
val TextDarkRed = Color(0xFFA62A2A)
val TextOliveGreen = Color(0xFF7B8B42)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgroAlertsScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { /* Acción del menú (Drawer) */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú",
                            tint = Color(0xFF047435),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenTopBar)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = GreenTopBar,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(68.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Ir a HomeScreen",
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
            // 1. Encabezado: Logo y Perfil
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_upapp),
                    contentDescription = "Logo UPAPP - Ir a Home",
                    modifier = Modifier
                        .height(55.dp)
                        .clickable {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.perfil_avatar),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Icono de Alerta y Títulos
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

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Botones con Imagen

            // HUERTO ESCOLAR
            AgroCard(
                imageRes = R.drawable.huerto,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://raicezqueconectan.com/nido.html"))
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // CAMBIOS CLIMÁTICOS DE LA ZONA
            AgroCard(
                imageRes = R.drawable.clima,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://raicezqueconectan.com/antena.html"))
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // AVISTAMIENTO DE LUCIÉRNAGAS
            AgroCard(
                imageRes = R.drawable.luciernagas,
                onClick = {
                    Toast.makeText(
                        context,
                        "Fuera de servicio. Próximamente disponible",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Botón Ver más
            Button(
                onClick = { /* Acción Ver más */ },
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

// Componente reutilizable para las tarjetas tipo píldora
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