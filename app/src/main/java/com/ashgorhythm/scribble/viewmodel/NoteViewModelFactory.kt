package com.ashgorhythm.scribble.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashgorhythm.scribble.domain.NoteRepo
@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(private val noteRepo: NoteRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(noteRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}