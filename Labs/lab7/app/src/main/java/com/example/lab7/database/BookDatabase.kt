package com.example.lab7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 2, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDAO(): BookDAO

    companion object{
        var INSTANCE: BookDatabase? = null

        //get reference to database OR create one
        fun getDatabase(context: Context): BookDatabase{
            val tempInstance = INSTANCE
            //return instance if it exists already
            if(tempInstance != null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, BookDatabase::class.java, "book_database")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}