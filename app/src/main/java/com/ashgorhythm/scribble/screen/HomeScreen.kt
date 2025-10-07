package com.ashgorhythm.scribble.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.navigation.Screen
import com.ashgorhythm.scribble.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: NoteViewModel,
    navController: NavHostController,
    onNoteClick: (Long) -> Unit
){
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,

        topBar = {
            TopAppBar(
                title = { Text("Scribble", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, "This function will be added shortly.", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Default.Search,
                            "Search",
                            tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.clearNote()
                  navController.navigate(Screen.Note.createRoute(-1L))
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(2.dp),
                contentColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Default.Add,"Add")
            }
        }
    ) {paddingValues ->
        Column() {
            CategoryFilterChipGroup(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    viewModel.selectCategory(category)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (notes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        "No notes found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    contentPadding = paddingValues,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                        8.dp
                    ),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    items(notes) { note ->
                        NoteCard(
                            note = note,
                            onNoteClick = onNoteClick
                        )
                    }
                }

            }
        }

    }

}


@Composable
fun NoteCard(
    note: Note,
    onNoteClick: (Long) -> Unit
){
    Card(
        onClick = {onNoteClick(note.id)},
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Text(
            text = note.title,
            maxLines = 2,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp),
            color = Color.Black
        )
        if (note.description.isNotBlank()){
            Text(
                text = note.description,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).padding(top = 8.dp),
                color = Color.Black
            )
        }

        Text(text = if (note.updatedAt != note.createdAt) {
                "Updated ${formatDate(note.updatedAt)}"
            } else {
                "Created ${formatDate(note.createdAt)}"
            },
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                modifier = Modifier.padding(12.dp).padding(top = 4.dp)
            )


    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}



