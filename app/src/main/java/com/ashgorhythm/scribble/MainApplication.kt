package com.ashgorhythm.scribble

import android.app.Application
import androidx.room.Room
import com.ashgorhythm.scribble.data.NoteDatabase

class MainApplication: Application() {
    companion object{
        lateinit var noteDatabase: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        noteDatabase = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "Notes_DB"
        ).build()
    }
}