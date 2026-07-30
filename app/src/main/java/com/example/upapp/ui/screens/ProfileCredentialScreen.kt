package com.example.upapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.upapp.ui.components.QrCardView
import com.example.upapp.ui.theme.CreamYellow
import com.example.upapp.ui.theme.DarkGray
import com.example.upapp.ui.theme.DarkGreen
import com.example.upapp.ui.theme.RosePink
import com.example.upapp.ui.theme.UPAPPTheme

/**
 * Datos necesarios para mostrar la credencial.
 *
 * Después pueden obtenerse desde una API o base de datos.
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
 * Datos de prueba tomados del prototipo.
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
            /*
             * Botón de salida.
             *
             * La imagen debe existir en:
             * app/src/main/res/drawable/exit_pink.png
             */
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
                    .size(48.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.exit_pink
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
                        top = 30.dp,
                        bottom = 26.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePlaceholder()

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(18.dp))

                /*
                 * En Android Studio Preview aparecerá el texto QR.
                 *
                 * En el emulador o teléfono aparecerá
                 * el código QR verdadero.
                 */
                QrCardView(
                    qrContent = credential.qrContent,
                    qrSize = 170.dp,
                    foregroundColor = Color.White,
                    backgroundColor = RosePink
                )

                Spacer(modifier = Modifier.height(18.dp))

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
        }
    }
}

/**
 * Fotografía provisional.
 *
 * Posteriormente puede reemplazarse por la imagen
 * real de la estudiante.
 */
@Composable
private fun ProfilePlaceholder() {
    val ovalShape = RoundedCornerShape(percent = 50)

    Box(
        modifier = Modifier
            .size(
                width = 175.dp,
                height = 125.dp
            )
            .clip(ovalShape)
            .background(CreamYellow)
            .border(
                width = 4.dp,
                color = DarkGreen,
                shape = ovalShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Fotografía del estudiante",
            modifier = Modifier.size(78.dp),
            tint = DarkGray
        )
    }
}

/**
 * Fila para mostrar cada dato de la credencial.
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