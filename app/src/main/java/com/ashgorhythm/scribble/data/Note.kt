package com.ashgorhythm.scribble.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashgorhythm.scribble.domain.NoteCategory

@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val createdAt: Long,
    @ColumnInfo val updatedAt: Long,
    @ColumnInfo val category: String = NoteCategory.Other.name,
    @ColumnInfo val imageUri: String? = null
){
    fun getCategoryObject(): NoteCategory{
        return NoteCategory.fromName(category)
    }
}