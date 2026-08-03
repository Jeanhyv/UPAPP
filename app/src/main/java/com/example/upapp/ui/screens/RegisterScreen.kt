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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upapp.data.UserPreferences
import com.example.upapp.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackToLoginClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    fun handleRegister() {
        errorMessage = null

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorMessage = "Por favor completa todos los campos."
            return
        }

        if (!email.endsWith("@upatlautla.edu.mx")) {
            errorMessage = "Ingresa un correo institucional válido (@upatlautla.edu.mx)."
            return
        }

        if (password.length < 8) {
            errorMessage = "La contraseña debe tener al menos 8 caracteres."
            return
        }

        if (password != confirmPassword) {
            errorMessage = "Las contraseñas no coinciden."
            return
        }

        isLoading = true
        scope.launch {
            // 🟢 Guardar usuario localmente en DataStore
            userPrefs.registerUser(email, password)
            isLoading = false
            onRegisterSuccess() // Navega a HomeScreen
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
            // Título UPAPP
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(text = "UP", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
                Text(text = "Å", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
                Text(text = "PP", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = DarkGray)
            }

            Text(
                text = "Crear nueva cuenta",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkGray,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Campo Correo
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (errorMessage != null) errorMessage = null
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (it.length <= 8) {
                        password = it
                        if (errorMessage != null) errorMessage = null
                    }
                },
                placeholder = { Text("Contraseña (8 dígitos)", color = DarkGray.copy(alpha = 0.6f)) },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Campo Confirmar Contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    if (it.length <= 8) {
                        confirmPassword = it
                        if (errorMessage != null) errorMessage = null
                    }
                },
                placeholder = { Text("Confirmar contraseña", color = DarkGray.copy(alpha = 0.6f)) },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

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

            Button(
                onClick = { handleRegister() },
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
                    Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }

            TextButton(
                onClick = onBackToLoginClick,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = DarkGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    UPAPPTheme {
        RegisterScreen()
    }
}