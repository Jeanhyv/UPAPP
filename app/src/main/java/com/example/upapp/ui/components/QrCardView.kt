package com.example.upapp.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.ui.theme.DarkGreen
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

/**
 * Componente reutilizable para mostrar códigos QR.
 *
 * En Android Studio Preview puede mostrar un marcador ligero,
 * evitando problemas de renderizado.
 *
 * En un emulador o dispositivo físico genera el QR verdadero.
 */
@Composable
fun QrCardView(
    qrContent: String,
    modifier: Modifier = Modifier,
    qrSize: Dp = 210.dp,
    foregroundColor: Color = DarkGreen,
    backgroundColor: Color = Color.White,
    renderRealQrInPreview: Boolean = false
) {
    val isPreview = LocalInspectionMode.current

    /*
     * El QR real se genera cuando:
     *
     * 1. La aplicación se ejecuta en un emulador o teléfono.
     * 2. Se solicita expresamente dentro de una Preview individual.
     */
    val shouldGenerateRealQr =
        !isPreview || renderRealQrInPreview

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        if (shouldGenerateRealQr) {
            val qrBitmap = remember(
                qrContent,
                foregroundColor,
                backgroundColor
            ) {
                generateQrBitmap(
                    content = qrContent,
                    foregroundColor = foregroundColor.toArgb(),
                    backgroundColor = backgroundColor.toArgb()
                )
            }

            Image(
                bitmap = qrBitmap.asImageBitmap(),
                contentDescription = "Código QR de validación",
                modifier = Modifier
                    .padding(12.dp)
                    .size(qrSize),
                filterQuality = FilterQuality.None
            )
        } else {
            /*
             * Marcador que se muestra solamente dentro
             * de la Preview de Android Studio.
             */
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(qrSize)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "QR",
                    color = foregroundColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Genera el Bitmap del código QR real.
 */
private fun generateQrBitmap(
    content: String,
    foregroundColor: Int,
    backgroundColor: Int
): Bitmap {
    val size = 700

    require(content.isNotBlank()) {
        "El contenido del código QR no puede estar vacío."
    }

    val hints = mapOf(
        EncodeHintType.CHARACTER_SET to "UTF-8",
        EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.M,
        EncodeHintType.MARGIN to 1
    )

    val bitMatrix = MultiFormatWriter().encode(
        content,
        BarcodeFormat.QR_CODE,
        size,
        size,
        hints
    )

    val pixels = IntArray(size * size)

    for (y in 0 until size) {
        for (x in 0 until size) {
            pixels[y * size + x] =
                if (bitMatrix[x, y]) {
                    foregroundColor
                } else {
                    backgroundColor
                }
        }
    }

    return Bitmap.createBitmap(
        pixels,
        size,
        size,
        Bitmap.Config.ARGB_8888
    )
}

/**
 * Preview individual del QR verdadero.
 */
@Preview(
    name = "Código QR",
    showBackground = true
)
@Composable
private fun QrCardViewPreview() {
    QrCardView(
        qrContent = "UPAPP-CREDENTIAL-25308065",
        foregroundColor = DarkGreen,
        backgroundColor = Color.White,
        renderRealQrInPreview = false
    )
}