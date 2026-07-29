package com.example.upapp.screens

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    // Estados de entrada del usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estados para gestión de errores y carga
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // -------------------------------------------------------------
    // CREDANCIALES PREDEFINIDAS (Simulación temporal)
    // -------------------------------------------------------------
    val dummyEmail = "UPAPP@upatlautla.edu.mx"
    val dummyPassword = "12345678"

    // -------------------------------------------------------------
    // LÓGICA DE AUTENTICACIÓN (Preparada para Base de Datos)
    // -------------------------------------------------------------
    fun authenticateUser() {
        // Limpiamos errores previos
        errorMessage = null

        // 1. Validaciones básicas de entrada
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor ingresa tu correo y contraseña."
            return
        }

        if (password.length < 8) {
            errorMessage = "La contraseña debe tener exactamente 8 dígitos."
            return
        }

        // 2. Simulación de petición de red / Base de datos
        isLoading = true
        scope.launch {
            // Simula un retraso de red (p. ej. consulta a API / Firebase / SQLite)
            delay(1000)

            isLoading = false

            // TODO: En el futuro, reemplazar esta condición por la consulta real a BD:
            // val isValid = userRepository.login(email, password)
            if (email.trim() == dummyEmail && password == dummyPassword) {
                onLoginSuccess() // Navega a HomeScreen
            } else {
                errorMessage = "Correo o contraseña incorrectos."
            }
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

            // Subtítulo Bienvenido
            Text(
                text = "Bienvenido",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkGray,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Campo Correo Institucional
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (errorMessage != null) errorMessage = null
                },
                placeholder = { Text("Email", color = DarkGray.copy(alpha = 0.6f)) },
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

            // Campo Contraseña (8 dígitos)
            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (it.length <= 8) {
                        password = it
                        if (errorMessage != null) errorMessage = null
                    }
                },
                placeholder = { Text("Contraseña", color = DarkGray.copy(alpha = 0.6f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = CreamYellow.copy(alpha = 0.3f),
                    focusedContainerColor = CreamYellow.copy(alpha = 0.3f),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryGreen
                ),
                singleLine = true,
                isError = errorMessage != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Mensaje de Error (si existe)
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = CrimsonRed,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botón Iniciar Sesión
            Button(
                onClick = { authenticateUser() },
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
                        text = "Iniciar sesión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Enlace Olvidaste tu contraseña (manda a Servicios/Soporte)
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = DarkGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    UPAPPTheme {
        LoginScreen()
    }
}