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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.upapp.components.AppTopBar
import com.example.upapp.components.AppDrawer
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
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onNavigateToCalendar = {
                    navController.navigate(Screen.CalendarEvents.route)
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

                // Tarjetas con links / acciones
                AgroCard(
                    imageRes = R.drawable.huerto,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://raicezqueconectan.com/nido.html"))
                        context.startActivity(intent)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                AgroCard(
                    imageRes = R.drawable.clima,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://raicezqueconectan.com/antena.html"))
                        context.startActivity(intent)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Ver más
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