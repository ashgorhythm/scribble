package com.ashgorhythm.scribble.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ashgorhythm.scribble.domain.NoteCategory

/**
 * Reusable chip group for category selection in Home screen
 * Shows ALL + selectable categories
 * Uses sealed class for type-safe category handling
 */
@Composable
fun CategoryFilterChipGroup(
    selectedCategory: NoteCategory,
    onCategorySelected: (NoteCategory) -> Unit,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(NoteCategory.getAllCategories()){category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = {onCategorySelected(category)},
                label = { Text(category.displayName) }
            )

        }
    }
}
/**
 * Reusable chip group for category selection in Note screen
 * Only shows selectable categories (excludes All)
 */
@Composable
fun CategorySelectionChipGroup(
    selectedCategory: NoteCategory,
    onCategorySelected: (NoteCategory) -> Unit,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(NoteCategory.getSelectableCategories()){category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = {onCategorySelected(category)},
                label = { Text(category.displayName) },
            )

        }
    }
}