package com.navdroid.bmicalculator.app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.navdroid.bmicalculator.app.theme.AppTheme
import com.navdroid.bmicalculator.app.ui.HomeScreen
import com.navdroid.bmicalculator.app.ui.ResultScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ComposeApp()
            }
        }
    }

    override fun onBackPressed() {
        if (ComposeStatus.currentScreen == Screen.Home) {
            onBackPressedDispatcher.addCallback(this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        finish()
                    }
                })
        }
    }
}

@Composable
fun ComposeApp() {
    AppTheme {
        AppContent()
    }
}

// todo optimize navigation process
@Composable
fun AppContent() {
    Crossfade(ComposeStatus.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen()
                is Screen.Result -> ResultScreen(bmi = screen.bmi)
            }
        }
    }
}
