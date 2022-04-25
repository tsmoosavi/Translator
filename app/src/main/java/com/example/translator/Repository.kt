package com.example.translator

import android.content.Context

class Repository {
    var db: WordDataBase? = null
    var wordDao: DaoOfWord? = null
    fun startDb(context: Context){
        db = WordDataBase.getAppDataBase(context)
        wordDao =db?.wordDao()
    }
}