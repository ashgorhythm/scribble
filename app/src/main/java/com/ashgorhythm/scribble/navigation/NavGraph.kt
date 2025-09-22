package com.ashgorhythm.scribble.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ashgorhythm.scribble.domain.MainApplication
import com.ashgorhythm.scribble.domain.NoteRepo
import com.ashgorhythm.scribble.screen.HomeScreen
import com.ashgorhythm.scribble.screen.NoteScreen
import com.ashgorhythm.scribble.viewmodel.NoteViewModel
import com.ashgorhythm.scribble.viewmodel.NoteViewModelFactory

@Composable
fun ScribbleNavGraph(
    navController: NavHostController,
    noteViewModel: NoteViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(noteViewModel,navController)
        }
        composable(Screen.Note.route){
            NoteScreen(navController)
        }
    }
}