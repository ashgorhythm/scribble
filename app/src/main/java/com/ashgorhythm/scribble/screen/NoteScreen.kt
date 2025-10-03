package com.ashgorhythm.scribble.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.ashgorhythm.scribble.R
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.viewmodel.NoteViewModel


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavHostController,
    viewModel: NoteViewModel,
    noteId: Long
){
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var existingNote by remember { mutableStateOf<Note?>(null) }
    val note = viewModel.note.collectAsState().value
    val context = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }
    val isNewNote = noteId == -1L

    LaunchedEffect(noteId) {
        if (!isNewNote){
            viewModel.getById(noteId)
        }
        else{
            viewModel.clearNote()
            existingNote = null
            title = ""
            description = ""

        }


    }
    LaunchedEffect(note) {
        note?.let {
            existingNote = it
            title = it.title
            description = it.description
        }

    }



    Dialog(
        onDismissRequest = {navController.popBackStack()},
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
            Box(modifier = Modifier.fillMaxSize())
            {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            if (existingNote != null){
                                Text(existingNote!!.title, color = Color.Black)
                            }
                            else{
                                Text("New Note", color = Color.Black)
                            }

                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(Icons.Default.Close,"Close", tint = Color.Black)
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                if (title.isBlank() && description.isBlank()){
                                    openAlertDialog = true
                                }
                                else{
                                    viewModel.deleteNote(existingNote!!)
                                    navController.popBackStack()
                                    Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Icon(Icons.Filled.Delete, "Delete", tint = Color.Black)
                            }
                        },

                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        value = title,
                        onValueChange = { title = it },
                        placeholder = {Text(
                            text = "Title",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold)},
                        textStyle = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
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
                        value = description,
                        onValueChange = { description = it},
                        placeholder = {Text(
                            text = "Description",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)},
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                }
                FloatingActionButton(
                    onClick = {
                        val newNote = existingNote?.copy(
                            title = title,
                            description = description,
                            updatedAt = System.currentTimeMillis()
                        )
                            ?: Note(
                                title = title,
                                description = description,
                                createdAt = System.currentTimeMillis(),
                                updatedAt = System.currentTimeMillis()
                            )
                        if (title.isBlank() && description.isBlank()){
                            openAlertDialog = true
                        }
                        else{
                            viewModel.upsertNote(newNote)
                            navController.popBackStack()
                            if (isNewNote){
                                Toast.makeText(context, "Note saved successfully", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context, "Note updated successfully", Toast.LENGTH_SHORT).show()
                            }

                        }

                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 35.dp)
                        .padding(end = 16.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black
                ) {
                    Icon(painter = painterResource(R.drawable.outlined_save), contentDescription = "Save", tint = Color.Black)
                }
                if (openAlertDialog) {
                    AlertNote(
                        onDismissRequest = { openAlertDialog = false },
                        dialogTitle = "Empty Note!",
                        dialogText = "Title or Description or both are empty.Please write something.Empty note can't be saved or deleted.",
                        icon = Icons.Filled.Info
                    )
                }
            }

        }
    }
}
