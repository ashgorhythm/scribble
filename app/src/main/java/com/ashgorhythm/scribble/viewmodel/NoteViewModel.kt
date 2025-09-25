package com.ashgorhythm.scribble.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.domain.NoteRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepo: NoteRepo
): ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes
    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note

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
//    fun searchNote(query: String){
//        viewModelScope.launch {
//            noteRepo.searchNote(query).collect {result ->
//                _notes.value =  result
//            }
//        }
//    }


}