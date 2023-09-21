package com.example.noteapp.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.ContactDao
import com.example.noteapp.model.Note_model

@Database(entities = [Note_model::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun contactDao():ContactDao
}

class Database{

    companion object{
        private lateinit var appDatabase: AppDatabase

        fun getDatabase(context: Context):AppDatabase{
            if (!::appDatabase.isInitialized){
                appDatabase= Room

                    .databaseBuilder(context,AppDatabase::class.java,"contacts")
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase
        }
    }
}