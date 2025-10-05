package com.ashgorhythm.scribble.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.domain.Category
import com.ashgorhythm.scribble.domain.NoteRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepo: NoteRepo
): ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes
    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    val filteredNotes: List<Note>
        get() = if (_selectedCategory.value == null){
            notes.value
        } else{
            notes.value.filter { it.category == _selectedCategory.value }
        }

    init {
        loadNotes()
    }

    fun loadNotes(){
        viewModelScope.launch {
            noteRepo.getAllNote().collect { list ->
                _notes.value = list
            }
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }
    }
    fun upsertNote(note: Note){
        viewModelScope.launch {
            noteRepo.upsertNote(note)
        }
    }
    fun getById(id: Long) {
        viewModelScope.launch {
           noteRepo.getByID(id).collect { note ->
               _note.value = note
           }
        }
    }
    fun clearNote(){
        _note.value = null
    }
    fun setCategory(category: Category?){
        _selectedCategory.value = category
    }
//    fun searchNote(query: String){
//        viewModelScope.launch {
//            noteRepo.searchNote(query).collect {result ->
//                _notes.value =  result
//            }
//        }
//    }


}