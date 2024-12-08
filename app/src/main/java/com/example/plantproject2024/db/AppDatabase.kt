package com.example.plantproject2024.db

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantproject2024.db.model.Message
import com.example.plantproject2024.db.model.Plants
import com.example.plantproject2024.db.model.User

@Database(entities = [Plants::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun plantDAO(): PlantDAO

    companion object {
        //Ensure only one instance of the database is created
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Plant Project 2024"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}