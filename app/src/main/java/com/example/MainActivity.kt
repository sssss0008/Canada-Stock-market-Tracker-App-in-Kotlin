package com.example

import android.content.Context
import android.os.Bundle
import java.io.File
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.ui.screens.MainScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MyApplicationTheme

enum class AppDestination {
    SPLASH,
    ONBOARDING,
    MAIN
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val baseCache = File(cacheDir, "WebView/Default/HTTP Cache")
            val jsCache = File(baseCache, "Code Cache/js")
            val wasmCache = File(baseCache, "Code Cache/wasm")
            if (!jsCache.exists()) jsCache.mkdirs()
            if (!wasmCache.exists()) wasmCache.mkdirs()
        } catch (_: Exception) {}
        enableEdgeToEdge()
        setContent {
            val systemDark = isSystemInDarkTheme()
            var isDark by remember { mutableStateOf(systemDark) }
            val context = LocalContext.current
            val prefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
            val hasSeenOnboarding = remember { prefs.getBoolean("has_completed_onboarding", false) }

            var currentDestination by remember { mutableStateOf(AppDestination.SPLASH) }

            MyApplicationTheme(darkTheme = isDark) {
                Crossfade(
                    targetState = currentDestination,
                    animationSpec = tween(400),
                    label = "app_destination_crossfade"
                ) { destination ->
                    when (destination) {
                        AppDestination.SPLASH -> {
                            SplashScreen(
                                onTimeout = {
                                    if (hasSeenOnboarding) {
                                        currentDestination = AppDestination.MAIN
                                    } else {
                                        currentDestination = AppDestination.ONBOARDING
                                    }
                                }
                            )
                        }
                        AppDestination.ONBOARDING -> {
                            OnboardingScreen(
                                onFinish = {
                                    prefs.edit().putBoolean("has_completed_onboarding", true).apply()
                                    currentDestination = AppDestination.MAIN
                                }
                            )
                        }
                        AppDestination.MAIN -> {
                            MainScreen(
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark },
                                onOpenOnboarding = {
                                    currentDestination = AppDestination.ONBOARDING
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}


