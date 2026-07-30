package com.example.upapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.upapp.R
import com.example.upapp.ui.components.AppTopBar
import com.example.upapp.ui.theme.CreamYellow
import com.example.upapp.ui.theme.DarkGray
import com.example.upapp.ui.theme.DarkGreen
import com.example.upapp.ui.theme.LightGreen
import com.example.upapp.ui.theme.LightSage
import com.example.upapp.ui.theme.RosePink
import com.example.upapp.ui.theme.SoftBlue
import com.example.upapp.ui.theme.UPAPPTheme

/**
 * Información de cada botón del menú principal.
 */
private data class HomeModule(
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val onClick: () -> Unit
)

/**
 * Pantalla principal de UPAPP.
 *
 * Todos los callbacks tienen un valor vacío temporal.
 * Más adelante se conectarán con NavGraph.
 */
@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {},
    onAgroAlertsClick: () -> Unit = {},
    onVehiclePassClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onDocumentsClick: () -> Unit = {},
    onRadioClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onEventsClick: () -> Unit = {}
) {
    /*
     * Controla si la credencial está visible.
     *
     * Debe estar dentro de una función @Composable.
     */
    var showCredential by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        /*
         * Pantalla principal.
         */
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                AppTopBar(
                    onMenuClick = onMenuClick,
                    onProfileClick = {
                        showCredential = true
                    }
                )
            },

            bottomBar = {
                HomeBottomBar()
            }
        ) { paddingValues ->

            HomeContent(
                paddingValues = paddingValues,
                onAgroAlertsClick = onAgroAlertsClick,
                onVehiclePassClick = onVehiclePassClick,
                onCalendarClick = onCalendarClick,
                onMapClick = onMapClick,
                onDocumentsClick = onDocumentsClick,
                onRadioClick = onRadioClick,
                onMoreClick = onMoreClick,
                onEventsClick = onEventsClick
            )
        }

        /*
         * La credencial aparece después del Scaffold.
         *
         * Por eso queda dibujada encima de HomeScreen.
         */
        AnimatedVisibility(
            visible = showCredential,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f),

            enter = fadeIn() + slideInHorizontally(
                initialOffsetX = { fullWidth ->
                    fullWidth
                }
            ),

            exit = fadeOut() + slideOutHorizontally(
                targetOffsetX = { fullWidth ->
                    fullWidth
                }
            )
        ) {
            ProfileCredentialOverlay(
                onCloseClick = {
                    showCredential = false
                }
            )
        }
    }
}

/**
 * Contenido desplazable de la pantalla principal.
 */
@Composable
private fun HomeContent(
    paddingValues: PaddingValues,
    onAgroAlertsClick: () -> Unit,
    onVehiclePassClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onMapClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    onRadioClick: () -> Unit,
    onMoreClick: () -> Unit,
    onEventsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(14.dp))

        BannerSection()

        Spacer(modifier = Modifier.height(22.dp))

        GridMenuSection(
            onAgroAlertsClick = onAgroAlertsClick,
            onVehiclePassClick = onVehiclePassClick,
            onCalendarClick = onCalendarClick,
            onMapClick = onMapClick,
            onDocumentsClick = onDocumentsClick,
            onRadioClick = onRadioClick
        )

        Spacer(modifier = Modifier.height(28.dp))

        ActionButtonsSection(
            onMoreClick = onMoreClick,
            onEventsClick = onEventsClick
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Carrusel superior.
 *
 * Utiliza imágenes que ya existen en drawable.
 */
@Composable
private fun BannerSection() {
    val banners = listOf(
        R.drawable.clima,
        R.drawable.huerto,
        R.drawable.luciernagas
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        contentPadding = PaddingValues(horizontal = 22.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(banners) { banner ->

            Card(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {
                Image(
                    painter = painterResource(id = banner),
                    contentDescription = "Anuncio institucional",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

/**
 * Cuadrícula de seis módulos.
 */
@Composable
private fun GridMenuSection(
    onAgroAlertsClick: () -> Unit,
    onVehiclePassClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onMapClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    onRadioClick: () -> Unit
) {
    val modules = listOf(
        HomeModule(
            title = "Alertas",
            icon = Icons.Default.WarningAmber,
            backgroundColor = RosePink,
            onClick = onAgroAlertsClick
        ),
        HomeModule(
            title = "Tarjetón",
            icon = Icons.Default.DirectionsCar,
            backgroundColor = CreamYellow,
            onClick = onVehiclePassClick
        ),
        HomeModule(
            title = "Calendario",
            icon = Icons.Default.CalendarMonth,
            backgroundColor = SoftBlue,
            onClick = onCalendarClick
        ),
        HomeModule(
            title = "Croquis",
            icon = Icons.Default.LocationOn,
            backgroundColor = CreamYellow,
            onClick = onMapClick
        ),
        HomeModule(
            title = "Documentos",
            icon = Icons.Default.Description,
            backgroundColor = LightSage,
            onClick = onDocumentsClick
        ),
        HomeModule(
            title = "UPA radio",
            icon = Icons.Default.Mic,
            backgroundColor = SoftBlue,
            onClick = onRadioClick
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        modules.chunked(3).forEach { rowModules ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowModules.forEach { module ->

                    HomeModuleCard(
                        module = module,
                        modifier = Modifier.weight(1f)
                    )
                }

                /*
                 * Mantiene el tamaño uniforme si alguna fila
                 * llegara a contener menos de tres módulos.
                 */
                repeat(3 - rowModules.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Tarjeta individual de un módulo.
 */
@Composable
private fun HomeModuleCard(
    module: HomeModule,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = module.onClick,
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = module.backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = module.icon,
                contentDescription = module.title,
                modifier = Modifier.size(40.dp),
                tint = DarkGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = module.title,
                color = DarkGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

/**
 * Botones inferiores de acciones.
 */
@Composable
private fun ActionButtonsSection(
    onMoreClick: () -> Unit,
    onEventsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Button(
            onClick = onMoreClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightSage,
                contentColor = DarkGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Ver más",
                fontWeight = FontWeight.SemiBold
            )
        }

        Button(
            onClick = onEventsClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SoftBlue,
                contentColor = DarkGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.Event,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Eventos",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Barra inferior de inicio.
 */
@Composable
private fun HomeBottomBar() {
    BottomAppBar(
        containerColor = LightGreen,
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    // Ya se encuentra en la pantalla de inicio.
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    modifier = Modifier.size(34.dp),
                    tint = DarkGreen
                )
            }
        }
    }
}

/**
 * Alias temporal.
 *
 * Permite que el proyecto siga funcionando si NavGraph
 * todavía llama a UpappScreen().
 */
@Composable
fun UpappScreen() {
    HomeScreen()
}

@Preview(
    name = "Pantalla principal",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun HomeScreenPreview() {
    UPAPPTheme {
        HomeScreen()
    }
}