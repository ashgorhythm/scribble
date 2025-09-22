package com.ashgorhythm.scribble.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavHostController){
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        OutlinedTextField(
                            value = "a",
                            onValueChange = { },
                            placeholder = {Text("Search Scribble")},
                            shape = CircleShape,
                            modifier = Modifier
                                .height(48.dp), // makes it pill shaped
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.LightGray,
                                focusedContainerColor = Color.Transparent
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(
                           onClick = {
                               navController.popBackStack()
                           }
                        ) {
                            Icon(Icons.Default.Close,"Close")
                        }
                    },

                )
            }
        }
    }
}