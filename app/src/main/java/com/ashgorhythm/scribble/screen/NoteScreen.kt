package com.ashgorhythm.scribble.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.viewmodel.NoteViewModel


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavHostController,
    viewModel: NoteViewModel,
    existingNote: Note? = null
){
    var title by remember { mutableStateOf(existingNote?.title) }
    var description by remember { mutableStateOf(existingNote?.description) }

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
                        if (existingNote?.title?.isNotBlank() == true){
                            Text("${existingNote.title}")
                        }
                        else{
                            Text("New Note")
                        }

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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
                )
                TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        value = title?: "",
                        onValueChange = { title = it },
                        placeholder = {Text("Title")},
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.LightGray
                )
                TextField(
                        modifier = Modifier
                            .fillMaxSize(),
                        value = description?: "",
                        onValueChange = { description = it},
                        placeholder = {Text("Description")},
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    )

            }
        }
    }
}
fun saveNote(
    note: Note
){

}