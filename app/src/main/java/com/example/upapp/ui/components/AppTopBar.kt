package com.example.upapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.upapp.R
import com.example.upapp.ui.screens.ProfileCredentialOverlay

// Colores del tema
val GreenBarColor = Color(0xFFC7D6BA)
val DarkGreenIcon = Color(0xFF1B5E20)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // Estado interno para saber si la credencial está visible
    var showCredentialOverlay by remember { mutableStateOf(false) }

    Box {
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
                IconButton(
                    onClick = {
                        // Solo abre el menú de hamburguesa si la credencial NO está abierta
                        if (!showCredentialOverlay) {
                            onMenuClick()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú de opciones",
                        tint = DarkGreenIcon,
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        onProfileClick()
                        showCredentialOverlay = true
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil_avatar),
                        contentDescription = "Perfil del usuario",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = GreenBarColor
            )
        )

        // CAPA SUPERPUESTA DE LA CREDENCIAL + BLOQUEO DE CLICS + DESLIZAR
        if (showCredentialOverlay) {
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue != SwipeToDismissBoxValue.Settled) {
                        showCredentialOverlay = false
                        true
                    } else {
                        false
                    }
                }
            )

            // 1. Capa de bloqueo global (Impide tocar el menú o los botones de atrás)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // Al tocar fuera de la tarjeta, también se cierra
                        showCredentialOverlay = false
                    }
            )

            // 2. Componente deslizable para cerrar (Swipe to Dismiss)
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {},
                content = {
                    ProfileCredentialOverlay(
                        onCloseClick = {
                            showCredentialOverlay = false
                        }
                    )
                }
            )
        }
    }
}