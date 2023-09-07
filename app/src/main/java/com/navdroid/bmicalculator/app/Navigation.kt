package com.navdroid.bmicalculator.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.navdroid.bmicalculator.app.util.BmiCalculator

/**
 * Class defining the screens we have in the app: home, article details and interests
 */
sealed class Screen {
    object Home : Screen()
    data class Result(val bmi: BmiCalculator) : Screen()
}

object ComposeStatus {
    var currentScreen by mutableStateOf<Screen>(Screen.Home)
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(destination: Screen) {
    ComposeStatus.currentScreen = destination
}
