package com.ashgorhythm.scribble.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.viewmodel.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    viewModel: NoteViewModel,
    existingNote: Note? = null,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var description by remember { mutableStateOf(existingNote?.description ?: "") }
    var noteColor by remember { mutableStateOf(Color(0xFFBCD0C7)) }
    var showExitDialog by remember { mutableStateOf(false) }

    val isContentChanged = remember(title, description) {
        title != (existingNote?.title ?: "")
        description != (existingNote?.description ?: "")
    }
    val displayTitle = if (title.isBlank()) {
        if (existingNote == null) "New Note" else "Edit Note"
    } else {
        title.take(20) + if (title.length > 20) "..." else ""
    }

    fun saveNote() {
        if (title.isNotBlank() || description.isNotBlank()) {
            val currentTime = System.currentTimeMillis()
            val note = if (existingNote != null) {
                existingNote.copy(
                    title = title,
                    description = description,
                    updatedAt = currentTime
                )
            } else {
                Note(
                    title = title,
                    description = description,
                    createdAt = currentTime,
                    updatedAt = 0
                )
            }
            viewModel.upsertNote(note)
        }
        onDismiss()
    }

    fun handleClose() {
        if (isContentChanged) {
            showExitDialog = true
        } else {
            onDismiss()
        }
    }
    Dialog(
        onDismissRequest = { handleClose() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = noteColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = displayTitle,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { handleClose() }) {
                            Icon(Icons.Default.Close, "Close")
                        }
                    },
                    actions = {
                        IconButton(onClick = { saveNote() }) {
                            Icon(Icons.Default.Check, "Save")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = noteColor.copy(alpha = 0.8f)
                    )

                )
                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    // Title Field
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) { innerTextField ->
                        Box {
                            if (title.isEmpty()) {
                                Text(
                                    text = "Title",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                    // Divider
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                    //Description Field
                    BasicTextField(
                        value = description,
                        onValueChange = { description = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp
                        ),
                        modifier = Modifier.fillMaxSize()
                    ) { innerTextField ->
                        Box {
                            if (description.isEmpty()) {
                                Text(
                                    text = "Start writing...",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }

                }
                //Bottom Bar
                BottomAppBar(
                    containerColor = noteColor.copy(alpha = 0.8f),
                    modifier = Modifier.height(56.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Color options
                        val colors = listOf(
                            Color(0xFFDEF2C8),
                            Color(0xFFC5DAC1),
                            Color(0xFFBCD0C7),
                            Color(0xFFA9B2AC),
                            Color(0xFF898980)
                        )
                        colors.forEach { color ->
                            Card(
                                modifier = Modifier
                                    .size(32.dp),
                                colors = CardDefaults.cardColors(containerColor = color),
                                onClick = { noteColor = color }
                            ) {
                                if (noteColor == color) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Selected",
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.Black.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
    // Exit confirmation dialog
    if (showExitDialog){
        AlertDialog(
            onDismissRequest = {showExitDialog = false},
            title = { Text("Save changes?") },
            text =  { Text("You have unsaved changes. Do you want to save them before closing?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        saveNote()
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onDismiss()
                    }
                ) {
                    Text("Discard")
                }
            }
        )


    }

}





