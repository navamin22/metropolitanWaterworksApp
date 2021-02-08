package com.example.landmarkapp.model.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.landmarkapp.model.Room.Entity.Queue
import com.example.landmarkapp.model.Room.Interface.QueueDao

@Database(entities = [Queue::class], version = 1)
abstract class LandmarkDatabase: RoomDatabase() {
    abstract fun queueDao(): QueueDao

    companion object{
        @Volatile
        private var instance : LandmarkDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            LandmarkDatabase::class.java,
            "landmark_db"
        ).build()

    }
}