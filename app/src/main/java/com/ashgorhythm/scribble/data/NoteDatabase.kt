package com.ashgorhythm.scribble.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ashgorhythm.scribble.domain.NoteCategory

/**
 * Room Database class
 * - Updated version to 2 to include category field
 * - Migration adds category column with default OTHER value
 */
@Database(entities = [Note::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun getNoteDao(): NoteDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE notes ADD COLUMN category TEXT NOT NULL DEFAULT '${NoteCategory.Other.name}'")
            }
        }
    }
}