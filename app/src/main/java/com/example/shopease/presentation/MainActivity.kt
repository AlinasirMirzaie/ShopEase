package com.example.shopease.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopease.presentation.navigation.ShopEaseNavGraph
import com.example.shopease.presentation.settings.SettingsViewModel
import com.example.shopease.presentation.theme.ShopEaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            ShopEaseTheme(darkTheme = isDarkMode) {
                CompositionLocalProvider(
                    LocalLayoutDirection
                    provides LayoutDirection.Rtl
                ) { ShopEaseNavGraph() }

            }
        }
    }
}