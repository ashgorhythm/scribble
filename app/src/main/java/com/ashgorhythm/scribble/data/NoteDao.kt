package com.ashgorhythm.scribble.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface NoteDao {
    //upsert note(insert + update)
    @Upsert
    suspend fun upsertNote(note: Note)
    //delete a note
    @Delete
    suspend fun deleteNote(note: Note)
    //get note
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>
    // Search notes by title or content
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<Note>>
}