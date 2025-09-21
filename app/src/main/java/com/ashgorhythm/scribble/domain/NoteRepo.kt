package com.ashgorhythm.scribble.domain

import androidx.room.Query
import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.data.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class NoteRepo(private val noteDao: NoteDao) {

    //Room does not return StateFlow, it only supports Flow or LiveData.
    fun getAllNote(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }
    suspend fun upsertNote(note: Note){
        return noteDao.upsertNote(note)
    }
    suspend fun deleteNote(note: Note){
        return noteDao.deleteNote(note)
    }
    fun searchNote(query: String): Flow<List<Note>>{
        return noteDao.searchNotes(query)
    }
}