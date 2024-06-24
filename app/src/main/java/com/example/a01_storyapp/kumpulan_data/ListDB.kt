package com.example.a01_storyapp.kumpulan_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a01_storyapp.kumpulan_response.ListStoryItem

@Database(
    entities = [ListStoryItem::class, RemotKeys::class],
    version = 2,
    exportSchema = false
)

abstract class ListDatabase: RoomDatabase() {

    abstract fun ceritaDao(): CeritaDao
    abstract fun remotKeysDao(): RemoteKeysDao

    companion object{
        @Volatile
        private var INSTANCE: ListDatabase? = null

        @JvmStatic
        fun mendapatkanDatabase(context: Context): ListDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ListDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}