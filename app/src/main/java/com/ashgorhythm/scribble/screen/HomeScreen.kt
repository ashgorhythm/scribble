package com.ashgorhythm.scribble.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: NoteViewModel){
    val notes by viewModel.notes.collectAsState()

    // State for showing NoteScreen dialog
    var showNoteScreen by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    // State for search functionality (for future implementation)
    var showSearchBar by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            TopAppBar(
                title = { Text("Scribble") },
                navigationIcon = {
                    IconButton(onClick = { showSearchBar = !showSearchBar }) {
                        Icon(Icons.Default.Search,"Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedNote = null  // Clear selection for new note
                    showNoteScreen = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(2.dp)
            ) {
                Icon(Icons.Default.Add,"Add")
            }
        }
    ) {paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(notes){note ->
               NoteCard(
                   note = note,
                   onClick = {
                       selectedNote = note  // Set selected note for editing
                       showNoteScreen = true
                   },
                   onLongClick = {
                       // Future: Show context menu for delete, share, etc.
                   }
               )
            }
        }
    }
    // Show NoteScreen dialog
    if (showNoteScreen) {
        NoteScreen(
            viewModel = viewModel,
            existingNote = selectedNote,
            onDismiss = {
                showNoteScreen = false
                selectedNote = null
            }
        )
    }
}


@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
){
    Card(
        onClick = onLongClick,
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(
            text = note.title,
            maxLines = 2,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp)
        )
        if (note.description.isNotBlank()){
            Text(
                text = note.description,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).padding(top = 8.dp)
            )
        }

        Text(text = if (note.updatedAt != note.createdAt) {
                "Updated ${formatDate(note.updatedAt)}"
            } else {
                "Created ${formatDate(note.createdAt)}"
            },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(12.dp).padding(top = 4.dp)
            )


    }
}
@Composable
fun EmptyNotesState(
    modifier: Modifier = Modifier,
    onCreateNote: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No notes yet",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap the + button to create your first note",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCreateNote,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(8.dp))
                Text("Create Note")
            }
        }
    }
}
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}



