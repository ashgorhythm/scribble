package com.ashgorhythm.scribble.navigation

sealed class Screen(var route: String) {
    object Home: Screen("home_screen")
    object Note: Screen("note_screen")
    object Splash: Screen("splash_screen")
}