package com.ashgorhythm.scribble.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ashgorhythm.scribble.R
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.navigation.Screen
import com.ashgorhythm.scribble.viewmodel.NoteViewModel
import kotlinx.coroutines.delay
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
    var isSearching by remember { mutableStateOf(false) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResult by viewModel.searchResults.collectAsStateWithLifecycle()
    val displayNotes = if (searchQuery.isNotBlank()) searchResult else notes



    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,

        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            placeholder = { Text("Search Notes") },
                            singleLine = true,
                            shape = CircleShape,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    viewModel.onSearchQueryChange(searchQuery)
                                }
                            )
                        )
                    } else {
                        Text("Scribble", color = Color.Black)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        isSearching = !isSearching
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
            ExpandableFab(
                onAddTextNote = {
                    viewModel.clearNote()
                    navController.navigate(Screen.Note.createRoute(-1L))
                },
                onAddImageNote = {
                    Toast.makeText(context, "Image note coming soon", Toast.LENGTH_SHORT).show()
                },
                onAddTodo = {
                    Toast.makeText(context, "Todo note coming soon", Toast.LENGTH_SHORT).show()
                },
                onAddAudio = {
                    Toast.makeText(context, "Audio note coming soon", Toast.LENGTH_SHORT).show()
                }
            )

        }
    ) {paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            CategoryFilterChipGroup(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    viewModel.selectCategory(category)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (displayNotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = if (searchQuery.isNotBlank()) "No matching notes found" else "No notes yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    items(displayNotes) { note ->
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
@Composable
fun ExpandableFab(
    onAddTextNote: () -> Unit,
    onAddImageNote: () -> Unit,
    onAddTodo: () -> Unit,
    onAddAudio: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Rotation animation for main FAB (+ → ×)
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        label = "fab_rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // All small FABs
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                // You can control order here: top-most is shown first
                AnimatedFab(icon = R.drawable.add_photo, text = "Image", delay = 0) {
                    onAddImageNote()
                }
                AnimatedFab(icon = null, text = "Text", delay = 100) {
                    onAddTextNote()
                }
                AnimatedFab(icon = null, text = "Todo", delay = 300) {
                    onAddTodo()
                }
                AnimatedFab(icon = R.drawable.outlined_mic, text = "Audio", delay =500 ) {
                    onAddAudio
                }

            }
        }

        // Main FAB (+)
        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.tertiary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
private fun AnimatedFab(
    icon: Int?,
    text: String,
    delay: Int,
    onClick: () -> Unit
) {
    val visible = remember { mutableStateOf(false) }

    // Appear staggered
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        visible.value = true
    }

    AnimatedVisibility(
        visible = visible.value,
        modifier = Modifier.zIndex(1f),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        SmallFloatingActionButton(
            onClick = {
                visible.value = false
                onClick()
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.tertiary
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text
                )
            } else {
                Text(text)
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}



