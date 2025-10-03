package com.ashgorhythm.scribble.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ashgorhythm.scribble.screen.HomeScreen
import com.ashgorhythm.scribble.screen.NoteScreen
import com.ashgorhythm.scribble.viewmodel.NoteViewModel

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
            HomeScreen(
                noteViewModel,
                navController,
               onNoteClick = {noteId ->
                   navController.navigate(Screen.Note.createRoute(noteId))
               }
            )
        }
        composable(
            Screen.Note.route,
            listOf(navArgument("noteId"){
                type = NavType.LongType
            })){ backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
            NoteScreen(navController,noteViewModel,noteId)
        }
    }
}