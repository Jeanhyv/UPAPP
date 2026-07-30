package com.example.upapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.upapp.R
import com.example.upapp.ui.theme.UPAPPTheme

@Composable
fun AppTopBar(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp)
        ) {
            // Botón de menú, alineado a la izquierda
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(52.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.menu
                    ),
                    contentDescription = "Abrir menú",
                    modifier = Modifier.size(30.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Logo siempre centrado
            Image(
                painter = painterResource(
                    id = R.drawable.logo_upapp
                ),
                contentDescription = "Logo de UPAPP",
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(135.dp)
                    .height(48.dp),
                contentScale = ContentScale.Fit
            )

            // Avatar, alineado a la derecha
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(52.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.perfil_avatar
                    ),
                    contentDescription = "Abrir credencial estudiantil",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(
                            RoundedCornerShape(percent = 50)
                        ),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(
    name = "Barra superior",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun AppTopBarPreview() {
    UPAPPTheme {
        AppTopBar(
            onMenuClick = {},
            onProfileClick = {}
        )
    }
}