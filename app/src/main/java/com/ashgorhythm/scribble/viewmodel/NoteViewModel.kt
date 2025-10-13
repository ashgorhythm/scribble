package com.ashgorhythm.scribble.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.domain.NoteCategory
import com.ashgorhythm.scribble.domain.NoteRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepo: NoteRepo
): ViewModel() {
    /**
     * Currently selected category for filtering in home screen
     * Default is All to show all notes
     * Using sealed class for type safety
     */
    private val _selectedCategory = MutableStateFlow<NoteCategory>(NoteCategory.All)
    val selectedCategory: StateFlow<NoteCategory> = _selectedCategory

    /**
     * Notes filtered by selected category
     * Uses flatMapLatest to switch flows when category changes
     * Automatically updates UI when category is changed
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val notes: StateFlow<List<Note>> = _selectedCategory
        .flatMapLatest { category ->
            noteRepo.getNotesByCategory(category)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()

        )
    /**
     * Selected note for detail/edit screen
     */
    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote
    /**
     * Category selected in note edit screen
     * Default to Other for new notes
     * Using sealed class for type safety
     */
    private val _noteCategory = MutableStateFlow<NoteCategory>(NoteCategory.Other)
    val noteCategory: StateFlow<NoteCategory> = _noteCategory

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Note>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    /**
     * Update the category for current note being edited
     */
    fun selectNoteCategory(category: NoteCategory) {
        _noteCategory.value = category
    }
    /**
     * Update the category filter in home screen
     * Takes sealed class parameter for type safety
     */
    fun selectCategory(category: NoteCategory) {
        _selectedCategory.value = category
    }


    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }
    }
    fun upsertNote(note: Note){
        viewModelScope.launch {
            val noteWithCategory = note.copy(category = _noteCategory.value.name)
            noteRepo.upsertNote(noteWithCategory)
        }
    }
//    fun getById(id: Long) {
//        viewModelScope.launch {
//           noteRepo.getByID(id).collect { note ->
//               _note.value = note
//           }
//        }
//    }
    fun clearNote(){
        _note.value = null
    }
    /**
     * Load a specific note by ID
     * Converts database string back to sealed class
     */
    fun loadNoteById(id: Long) {
        viewModelScope.launch {
            noteRepo.getByID(id).collect { note ->
                _selectedNote.value = note
                // Convert database string to sealed class object
                note?.let {
                    _noteCategory.value = NoteCategory.fromName(it.category)
                }
            }
        }
    }
    /**
     * Clear selected note and reset category to Other for new notes
     */
    fun resetNote() {
        _selectedNote.value = null
        _noteCategory.value = NoteCategory.Other
    }


    fun onSearchQueryChange(newQuery: String) {
    _searchQuery.value = newQuery
    viewModelScope.launch {
        noteRepo.searchNote(newQuery)
            .collect { results ->
                _searchResults.value = results
            }
    }
}


}