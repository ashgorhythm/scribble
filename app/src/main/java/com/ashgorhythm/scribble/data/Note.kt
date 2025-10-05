package com.ashgorhythm.scribble.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashgorhythm.scribble.domain.Category

@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val category: String = Category.Other.name,
    @ColumnInfo val createdAt: Long,
    @ColumnInfo val updatedAt: Long
)