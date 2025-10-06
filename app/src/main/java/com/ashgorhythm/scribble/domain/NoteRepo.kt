package com.ashgorhythm.scribble.domain

import com.ashgorhythm.scribble.data.Note
import com.ashgorhythm.scribble.data.NoteDao
import kotlinx.coroutines.flow.Flow

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
    fun getByID(id: Long): Flow<Note?> {
        return noteDao.getById(id)
    }
    fun getNotesByCategory(category: NoteCategory): Flow<List<Note>> {
        return if (category is  NoteCategory.All){
            getAllNote()
        }
        else {
           noteDao.getNotesByCategory(category.name)
        }
    }
//    fun searchNote(query: String): Flow<List<Note>>{
//        return noteDao.searchNotes(query)
//    }
}