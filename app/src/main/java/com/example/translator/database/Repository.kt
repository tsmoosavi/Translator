package com.example.translator.database

import android.content.Context
import androidx.lifecycle.LiveData

class Repository {
    var db: WordDataBase? = null
    var wordDao: DaoOfWord? = null
    fun startDb(context: Context){
        db = WordDataBase.getAppDataBase(context)
        wordDao =db?.wordDao()
    }
    fun getNumber():LiveData<Int>{
       return db!!.wordDao().countNumber()
    }
    suspend fun insertWord(word:WordEntity){
        db!!.wordDao().addWord(word)
    }
    suspend fun findWord(word:String):WordEntity{
        return db!!.wordDao().getWord(word)
    }
//    fun getId(id:Int):WordEntity{
//        return db!!.wordDao().findId(id)
//    }
    suspend fun delete(word:WordEntity){
        db!!.wordDao().delete(word)
    }
    suspend fun editWord(word:   WordEntity){
        db!!.wordDao().edit(word)
    }
    fun getList():LiveData<List<WordEntity>>{
         return db!!.wordDao().getList()
    }
}