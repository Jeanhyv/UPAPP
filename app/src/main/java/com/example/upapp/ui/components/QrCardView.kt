package com.example.upapp.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.upapp.ui.theme.DarkGreen
import com.example.upapp.ui.theme.UPAPPTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

/**
 * Componente reutilizable para mostrar un código QR.
 *
 * @param qrContent Texto o identificador que se guardará dentro del QR.
 * @param modifier Permite modificar la posición o tamaño desde otra pantalla.
 * @param qrSize Tamaño visual del código QR.
 * @param foregroundColor Color de los cuadros del QR.
 * @param backgroundColor Color de fondo del QR.
 */
@Composable
fun QrCardView(
    qrContent: String,
    modifier: Modifier = Modifier,
    qrSize: Dp = 210.dp,
    foregroundColor: Color = DarkGreen,
    backgroundColor: Color = Color.White
) {
    /*
     * remember evita generar nuevamente el QR en cada actualización
     * de la interfaz, siempre que los datos y colores no cambien.
     */
    val qrBitmap = remember(
        qrContent,
        foregroundColor,
        backgroundColor
    ) {
        generateQrBitmap(
            content = qrContent,
            size = 700,
            foregroundColor = foregroundColor.toArgb(),
            backgroundColor = backgroundColor.toArgb()
        )
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        Image(
            bitmap = qrBitmap.asImageBitmap(),
            contentDescription = "Código QR de validación",
            modifier = Modifier
                .padding(12.dp)
                .size(qrSize),
            /*
             * FilterQuality.None mantiene los cuadros del QR definidos
             * y evita que Android los difumine.
             */
            filterQuality = FilterQuality.None
        )
    }
}

/**
 * Convierte un texto en un Bitmap que contiene un código QR.
 */
private fun generateQrBitmap(
    content: String,
    size: Int,
    foregroundColor: Int,
    backgroundColor: Int
): Bitmap {
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
                if (bitMatrix[x, y]) foregroundColor else backgroundColor
        }
    }

    return Bitmap.createBitmap(
        pixels,
        size,
        size,
        Bitmap.Config.ARGB_8888
    )
}

@Preview(showBackground = true)
@Composable
private fun QrCardViewPreview() {
    UPAPPTheme {
        QrCardView(
            qrContent = "UPAPP-CREDENTIAL-25308065",
            foregroundColor = DarkGreen
        )
    }
}