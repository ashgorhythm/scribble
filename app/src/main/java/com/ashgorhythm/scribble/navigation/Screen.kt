package com.ashgorhythm.scribble.navigation

sealed class Screen(var route: String) {
    object Home : Screen(route = "home_screen")

    object Note : Screen("note_screen/{noteId}"){
        fun createRoute(noteId: Long): String {
            return "note_screen/${noteId}"
        }
    }



    object Splash : Screen("splash_screen")
}