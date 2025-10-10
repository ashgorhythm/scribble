package com.ashgorhythm.scribble.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

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

    //search by id
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Long): Flow<Note?>

    //category notes
    @Query("SELECT * FROM NOTES WHERE category = :category ORDER BY updatedAt DESC")
    fun getNotesByCategory(category: String): Flow<List<Note>>
    // Search notes by title or content
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<Note>>
}