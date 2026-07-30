package com.example.upapp.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.upapp.components.AppTopBar
import com.example.upapp.components.CustomAppDrawer
import com.example.upapp.ui.theme.*
import kotlinx.coroutines.launch

// Función para verificar conectividad de red
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@OptIn(UnstableApi::class)
@Composable
fun RadioScreen(
    onBackClick: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {}
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // URL del stream en vivo de la universidad
    val streamUrl = "https://exec-classics-differently-sim.trycloudflare.com/stream"
    var isPlaying by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Configuración HTTP DataSource para ExoPlayer
    val httpDataSourceFactory = remember {
        DefaultHttpDataSource.Factory()
            .setUserAgent("UPAPP-RadioPlayer/1.0 (Android)")
            .setAllowCrossProtocolRedirects(true)
            .setConnectTimeoutMs(15000)
            .setReadTimeoutMs(15000)
    }

    val mediaSourceFactory = remember {
        DefaultMediaSourceFactory(httpDataSourceFactory)
    }

    // Instancia de ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(streamUrl))
                    .setMimeType(MimeTypes.AUDIO_MPEG)
                    .build()
                setMediaItem(mediaItem)
                prepare()
            }
    }

    // Ciclo de vida y listeners de la reproducción
    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                isLoading = (playbackState == Player.STATE_BUFFERING)
            }

            override fun onPlayerError(error: PlaybackException) {
                isPlaying = false
                isLoading = false
                errorMessage = "Error de conexión con la señal en vivo."
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Colores del reproductor
    val headerFooterBg = Color(0xFFC7DCB8)
    val dialBackground = Color(0xFFCBE3EB)
    val darkGreenText = Color(0xFF1B7339)

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
                        /* Acción al tocar el perfil */
                    }
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerFooterBg)
                        .navigationBarsPadding()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = darkGreenText,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(dialBackground),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- DIAL DE RADIO EN CANVAS ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(top = 10.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val lineCount = 45
                        val spacing = width / (lineCount + 1)
                        val lineTop = 60f
                        val lineBottom = 170f

                        for (i in 1..lineCount) {
                            val x = spacing * i
                            drawLine(
                                color = Color.Black,
                                start = Offset(x, lineTop),
                                end = Offset(x, lineBottom),
                                strokeWidth = 3f
                            )
                        }

                        val needleX = spacing * 13.5f
                        val needleTop = 10f
                        val needleBottom = 185f

                        drawLine(
                            color = Color.Black,
                            start = Offset(needleX, needleTop),
                            end = Offset(needleX, needleBottom),
                            strokeWidth = 2.5f
                        )

                        drawCircle(
                            color = Color.Black,
                            radius = 4f,
                            center = Offset(needleX, needleBottom)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "86", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                        Text(text = "87", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                        Text(text = "88", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                        Text(text = "89", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                        Text(text = "90", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // --- FRECUENCIA DIGITAL ---
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "87.3",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MHz",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // --- ERRORES ---
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                // --- PERILLA CONCÉNTRICA / BOTÓN DE REPRODUCCIÓN ---
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFAFAFAF))
                        .clickable {
                            errorMessage = null
                            if (!isNetworkAvailable(context)) {
                                errorMessage = "Sin conexión a internet."
                                exoPlayer.pause()
                                return@clickable
                            }

                            if (isPlaying) {
                                exoPlayer.pause()
                            } else {
                                exoPlayer.play()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(115.dp)
                            .clip(CircleShape)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(36.dp),
                                color = Color.White,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                                modifier = Modifier.size(54.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}