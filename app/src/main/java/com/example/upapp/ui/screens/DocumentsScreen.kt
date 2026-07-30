package com.example.upapp.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.upapp.R

val GreenTopBarDoc = Color(0xFFC4D6B0)
val TextRedDoc = Color(0xFFD32F2F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(navController: NavController) {
    val context = LocalContext.current

    fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { /* Acción Menú */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú",
                            tint = Color(0xFF047435),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenTopBarDoc)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = GreenTopBarDoc,
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
            // 1. Encabezado (Logo UPAPP y Perfil)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_upapp),
                    contentDescription = "Logo UPAPP",
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

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Icono e Título Principal "Documentos"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.documentos),
                    contentDescription = "Icono Documentos",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Documentos",
                    color = TextRedDoc,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Tarjetas limpias (Sin texto superpuesto)

            // NSS
            DocumentCard(
                imageRes = R.drawable.nss,
                containerColor = Color(0xFFFCE4EC),
                onClick = { openWebPage("https://www.imss.gob.mx/tramites/imss02008") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Historial Académico
            DocumentCard(
                imageRes = R.drawable.historial,
                containerColor = Color(0xFFE8F5E9),
                onClick = { openWebPage("https://sisu.upatlautla.edu.mx/") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Carga Académica
            DocumentCard(
                imageRes = R.drawable.carga_academica,
                containerColor = Color(0xFFFFF8E1),
                onClick = { openWebPage("https://upa.edomex.gob.mx/sites/upa.edomex.gob.mx/files/images/MC%20Ingenier%C3%ADa%20en%20Tecnolog%C3%ADas%20de%20la%20Informaci%C3%B3n_0.pdf") }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// Componente optimizado: solo dibuja la tarjeta con tu imagen de fondo
@Composable
fun DocumentCard(
    imageRes: Int,
    containerColor: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit, // Se ajusta para que se vea la imagen completa
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}