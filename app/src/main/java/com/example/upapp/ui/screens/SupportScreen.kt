package com.example.upapp.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SupportScreen(
    onBackToLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var isSuccessMessageVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // -------------------------------------------------------------
    // LÓGICA DE RECUPERACIÓN (Preparada para envío de correo futuro)
    // -------------------------------------------------------------
    fun sendTemporaryPassword() {
        errorMessage = null
        isSuccessMessageVisible = false

        // 1. Validación de entrada
        if (email.isBlank()) {
            errorMessage = "Por favor ingresa tu correo institucional."
            return
        }

        if (!email.contains("@") || !email.contains(".")) {
            errorMessage = "Ingresa un formato de correo válido."
            return
        }

        // 2. Simulación de envío de correo
        isLoading = true
        scope.launch {
            // Simula el tiempo de respuesta del servidor (p. ej. Servicio SMTP / Firebase / API)
            delay(1200)

            isLoading = false

            // TODO: En el futuro, conectar aquí el servicio real de envío de correo:
            // authRepository.sendTemporaryPasswordEmail(email)

            isSuccessMessageVisible = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Título UPAPP estilo prototipo
            Row(
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "UP",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
                Text(
                    text = "Å",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonRed
                )
                Text(
                    text = "PP",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
            }

            // Subtítulo Recuperar Contraseña
            Text(
                text = "Recuperar contraseña",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Indicaciones para el usuario
            Text(
                text = "Ingresa tu correo institucional registrado. Te enviaremos una contraseña temporal para que puedas acceder.",
                fontSize = 14.sp,
                color = DarkGray.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Campo Correo Institucional
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (errorMessage != null) errorMessage = null
                    if (isSuccessMessageVisible) isSuccessMessageVisible = false
                },
                placeholder = { Text("Correo institucional", color = DarkGray.copy(alpha = 0.6f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = CreamYellow.copy(alpha = 0.3f),
                    focusedContainerColor = CreamYellow.copy(alpha = 0.3f),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryGreen
                ),
                singleLine = true,
                isError = errorMessage != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Mensaje de Error
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = CrimsonRed,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Mensaje de Éxito al presionar "Continuar"
            if (isSuccessMessageVisible) {
                Text(
                    text = "Se ha enviado una contraseña temporal al correo $email. Revisa tu bandeja de entrada.",
                    color = DarkGreen,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            if (errorMessage == null && !isSuccessMessageVisible) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botón Continuar
            Button(
                onClick = { sendTemporaryPassword() },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen,
                    contentColor = DarkGray
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = DarkGreen,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Continuar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Botón Regresar al Inicio de Sesión
            TextButton(
                onClick = onBackToLoginClick,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            ) {
                Text(
                    text = "← Volver al inicio de sesión",
                    color = DarkGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SupportScreenPreview() {
    UPAPPTheme {
        SupportScreen()
    }
}