package com.ashgorhythm.scribble.screen

import android.support.v4.app.INotificationSideChannel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun NoteScreen(){
    var text by remember{ mutableStateOf("Text") }
   Dialog(
       onDismissRequest = {},
       properties = DialogProperties(
           usePlatformDefaultWidth = false,
           decorFitsSystemWindows = false
       )
   ) {
       Surface(
           modifier = Modifier.fillMaxSize(),
           shadowElevation = 2.dp
       ) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
           ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "New Note",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 100.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.Close,"Close")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color(0xFFBCD0C7))
                )
               Column {
                   TextField(
                       modifier = Modifier.fillMaxWidth(),
                       value = text,
                       onValueChange = { text = it}
                   )
               }
           }
       }
   }
}