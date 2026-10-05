package com.example.citradataindonusa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.core_ui.AppTheme
import com.example.data.utils.NetworkEventManager
import com.example.features.nav.navhost.RootNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkEventManager: NetworkEventManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RootNavHost(
                    networkEventManager = networkEventManager
                )
            }
        }
    }
}
