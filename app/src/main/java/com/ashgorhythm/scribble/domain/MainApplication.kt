package com.ashgorhythm.scribble.domain

import android.app.Application
import androidx.room.Room
import com.ashgorhythm.scribble.data.NoteDatabase

class MainApplication: Application() {
    companion object{
        @Volatile
        private var INSTANCE: MainApplication? = null

        fun getInstance(): MainApplication {
            return INSTANCE ?: throw IllegalStateException("Application not initialized")
        }
    }

    val noteDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "Notes_DB"
        )
            .addMigrations(NoteDatabase.MIGRATION_1_2, NoteDatabase.MIGRATION_2_3)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}