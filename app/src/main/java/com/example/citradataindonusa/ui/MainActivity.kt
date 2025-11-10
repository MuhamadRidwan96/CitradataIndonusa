package com.example.citradataindonusa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.AppTheme
import com.example.features.nav.graph.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val projectId = intent?.getStringExtra("project_id")

        setContent {
            AppTheme {
                RootNavigationGraph(navController = rememberNavController(), projectId = projectId)
                SideEffect {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                }
            }
        }
    }
}
