package com.example.upapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.R
import com.example.upapp.ui.components.QrCardView
import com.example.upapp.ui.theme.RosePink
import com.example.upapp.ui.theme.UPAPPTheme

/**
 * Información que se muestra en la credencial.
 *
 * Posteriormente estos datos podrán obtenerse desde
 * una API, Firebase o una base de datos.
 */
data class CredentialUiData(
    val fullName: String,
    val studentNumber: String,
    val group: String,
    val rfc: String,
    val curp: String,
    val nss: String,
    val validFrom: String,
    val validUntil: String,
    val qrContent: String
)

/**
 * Datos temporales tomados del prototipo.
 */
private val sampleCredential = CredentialUiData(
    fullName = "Eloise Villa Fernández",
    studentNumber = "25308065",
    group = "632",
    rfc = "VFE070519",
    curp = "VFE070519MMCRXTA",
    nss = "50804564",
    validFrom = "31/10/2025",
    validUntil = "01/09/2026",
    qrContent = "UPAPP-CREDENTIAL-25308065"
)

/**
 * Pantalla completa de la credencial.
 *
 * Esta versión puede seguir utilizándose como una pantalla
 * independiente desde NavGraph.
 */
@Composable
fun ProfileCredentialScreen(
    credential: CredentialUiData = sampleCredential,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 20.dp,
                    vertical = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CredentialCard(
                credential = credential,
                onBackClick = onBackClick
            )
        }
    }
}

/**
 * Tarjeta rosa utilizada en la versión de pantalla completa.
 */
@Composable
private fun CredentialCard(
    credential: CredentialUiData,
    onBackClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 460.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = RosePink
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
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.exit
                    ),
                    contentDescription = "Regresar",
                    modifier = Modifier.size(42.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 22.dp,
                        end = 22.dp,
                        top = 22.dp,
                        bottom = 26.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileAvatar()

                Spacer(modifier = Modifier.height(16.dp))

                StudentHeader(
                    credential = credential
                )

                Spacer(modifier = Modifier.height(16.dp))

                CredentialDetails(
                    credential = credential
                )

                Spacer(modifier = Modifier.height(18.dp))

                CredentialQr(
                    credential = credential,
                    qrSize = 170
                )

                Spacer(modifier = Modifier.height(18.dp))

                CredentialValidity(
                    credential = credential
                )
            }
        }
    }
}

/**
 * Avatar circular del estudiante.
 */
@Composable
private fun ProfileAvatar() {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.perfil_avatar
            ),
            contentDescription = "Fotografía de Eloise Villa Fernández",
            modifier = Modifier
                .fillMaxSize()
                .scale(1.12f),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * Nombre y matrícula del estudiante.
 */
@Composable
private fun StudentHeader(
    credential: CredentialUiData
) {
    Text(
        text = credential.fullName,
        color = Color.White,
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(3.dp))

    Text(
        text = credential.studentNumber,
        color = Color.White,
        fontSize = 19.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

/**
 * Agrupa los datos personales de la credencial.
 */
@Composable
private fun CredentialDetails(
    credential: CredentialUiData
) {
    CredentialDetail(
        label = "Grupo",
        value = credential.group
    )

    CredentialDetail(
        label = "RFC",
        value = credential.rfc
    )

    CredentialDetail(
        label = "CURP",
        value = credential.curp
    )

    CredentialDetail(
        label = "NSS",
        value = credential.nss
    )
}

/**
 * Fila individual de información.
 */
@Composable
private fun CredentialDetail(
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
            modifier = Modifier.width(65.dp),
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Código QR de validación.
 */
@Composable
private fun CredentialQr(
    credential: CredentialUiData,
    qrSize: Int
) {
    QrCardView(
        qrContent = credential.qrContent,
        qrSize = qrSize.dp,
        foregroundColor = Color.White,
        backgroundColor = RosePink,

        /*
         * Déjalo en false para evitar que Android Studio
         * vuelva a mostrar una Preview en blanco.
         *
         * En el emulador y el teléfono se genera el QR real.
         */
        renderRealQrInPreview = false
    )
}

/**
 * Período de vigencia.
 */
@Composable
private fun CredentialValidity(
    credential: CredentialUiData
) {
    Text(
        text = "Vigencia",
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(2.dp))

    Text(
        text = "${credential.validFrom} - ${credential.validUntil}",
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}

/**
 * Credencial superpuesta sobre HomeScreen.
 *
 * Ocupa el 76 % del ancho y queda alineada a la derecha,
 * dejando parcialmente visible la pantalla principal.
 */
@Composable
fun ProfileCredentialOverlay(
    credential: CredentialUiData = sampleCredential,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        /*
         * Capa ligeramente oscura sobre HomeScreen.
         */
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.08f)
                )
        )

        /*
         * Panel rosa alineado a la derecha.
         */
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .fillMaxWidth(0.76f)
                .background(RosePink)
        ) {
            /*
             * Botón de cierre colocado sobre el contenido.
             */
            IconButton(
                onClick = onCloseClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.exit
                    ),
                    contentDescription = "Cerrar credencial",
                    modifier = Modifier.size(42.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 18.dp,
                        end = 18.dp,
                        top = 20.dp,
                        bottom = 24.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileAvatar()

                Spacer(modifier = Modifier.height(14.dp))

                StudentHeader(
                    credential = credential
                )

                Spacer(modifier = Modifier.height(16.dp))

                CredentialDetails(
                    credential = credential
                )

                Spacer(modifier = Modifier.height(18.dp))

                CredentialQr(
                    credential = credential,
                    qrSize = 165
                )

                Spacer(modifier = Modifier.height(16.dp))

                CredentialValidity(
                    credential = credential
                )
            }
        }
    }
}

/**
 * Preview de la versión completa.
 *
 * En Preview se mostrará el marcador "QR".
 * En la aplicación se genera el código verdadero.
 */
@Preview(
    name = "Credencial completa",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun ProfileCredentialScreenPreview() {
    UPAPPTheme {
        ProfileCredentialScreen()
    }
}