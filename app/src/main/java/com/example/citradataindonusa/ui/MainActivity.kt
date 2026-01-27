package com.example.citradataindonusa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import com.example.core_ui.AppTheme
import com.example.features.nav.navhost.RootNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                SideEffect {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                }
                RootNavHost()
            }
        }
    }
}
