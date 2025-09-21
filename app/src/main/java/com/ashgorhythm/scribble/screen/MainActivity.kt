package com.ashgorhythm.scribble.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ashgorhythm.scribble.domain.MainApplication
import com.ashgorhythm.scribble.domain.NoteRepo
import com.ashgorhythm.scribble.ui.theme.ScribbleTheme
import com.ashgorhythm.scribble.viewmodel.NoteViewModel
import com.ashgorhythm.scribble.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            NoteRepo(MainApplication.noteDatabase.getNoteDao())
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScribbleTheme() {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) {
                    HomeScreen(noteViewModel)
                }
            }
        }
    }
}