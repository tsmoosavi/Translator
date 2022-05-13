package com.example.translator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class],version = 1)
abstract class WordDataBase:RoomDatabase() {
    abstract fun wordDao(): DaoOfWord

    companion object{
        var INSTANCE: WordDataBase? = null

        fun getAppDataBase(context: Context): WordDataBase? {
            if (INSTANCE == null){
                synchronized(WordDataBase::class){
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext,
                            WordDataBase::class.java, "myDB",)
                            .build()
                }
            }
            return INSTANCE
        }

    }
}