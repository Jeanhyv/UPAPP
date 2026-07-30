package com.example.upapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.R
import com.example.upapp.components.AppTopBar
import com.example.upapp.components.CustomAppDrawer
import com.example.upapp.ui.components.QrCardView
import kotlinx.coroutines.launch

// Colores del tarjetón vehicular
private val VehicleCardGreen = Color(0xFFA6C9B7)
private val VehicleIconCream = Color(0xFFF1D18A)
private val VehicleTextWhite = Color.White
private val VehicleBackground = Color.White
private val GreenBottomBarVehicle = Color(0xFFC4D6B0)
private val DarkGreenHomeIcon = Color(0xFF047435)

data class VehiclePassUiData(
    val ownerName: String,
    val controlNumber: String,
    val vehicleType: String,
    val brand: String,
    val model: String,
    val color: String,
    val plate: String,
    val validity: String,
    val qrContent: String
)

private val sampleVehiclePass = VehiclePassUiData(
    ownerName = "Eloise Villa Fernández",
    controlNumber = "25308065",
    vehicleType = "Automóvil",
    brand = "Nissan",
    model = "Versa",
    color = "Gris",
    plate = "ABC-123-A",
    validity = "31/10/2025 - 01/09/2026",
    qrContent = "UPAPP-VEHICLE-25308065-ABC123A"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclePassScreen(
    vehiclePass: VehiclePassUiData = sampleVehiclePass,
    onBackClick: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            CustomAppDrawer(
                onNavigateToCalendar = {
                    scope.launch { drawerState.close() }
                    onNavigateToCalendar()
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
                        /* Acción de perfil */
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = GreenBottomBarVehicle,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(68.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Regresar a Inicio",
                                tint = DarkGreenHomeIcon,
                                modifier = Modifier.size(52.dp)
                            )
                        }
                    }
                }
            },
            containerColor = VehicleBackground
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VehiclePassCard(
                        vehiclePass = vehiclePass,
                        onBackClick = onBackClick
                    )
                }
            }
        }
    }
}

@Composable
private fun VehiclePassCard(
    vehiclePass: VehiclePassUiData,
    onBackClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 460.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = VehicleCardGreen
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
                    .size(48.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.exit),
                    contentDescription = "Regresar",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 22.dp,
                        end = 22.dp,
                        top = 28.dp,
                        bottom = 26.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VehicleIcon()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = vehiclePass.ownerName,
                    color = VehicleTextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = vehiclePass.controlNumber,
                    color = VehicleTextWhite,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                VehicleDetail(
                    label = "Tipo",
                    value = vehiclePass.vehicleType
                )

                VehicleDetail(
                    label = "Marca",
                    value = vehiclePass.brand
                )

                VehicleDetail(
                    label = "Modelo",
                    value = vehiclePass.model
                )

                VehicleDetail(
                    label = "Color",
                    value = vehiclePass.color
                )

                VehicleDetail(
                    label = "Placas",
                    value = vehiclePass.plate
                )

                Spacer(modifier = Modifier.height(18.dp))

                QrCardView(
                    qrContent = vehiclePass.qrContent,
                    qrSize = 170.dp,
                    foregroundColor = Color.White,
                    backgroundColor = VehicleCardGreen,
                    renderRealQrInPreview = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Vigencia",
                    color = VehicleTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = vehiclePass.validity,
                    color = VehicleTextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun VehicleIcon() {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(RoundedCornerShape(50))
            .background(VehicleIconCream),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.brum_brum),
            contentDescription = "Vehículo registrado",
            modifier = Modifier.size(110.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun VehicleDetail(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.width(75.dp),
            color = VehicleTextWhite,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = VehicleTextWhite,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(
    name = "Tarjetón vehicular",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun VehiclePassScreenPreview() {
    VehiclePassScreen()
}