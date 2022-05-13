package com.example.translator.database

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    var db: WordDataBase? = null
    var wordDao: DaoOfWord? = null
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    fun startDb(context: Context){
        db = WordDataBase.getAppDataBase(context)
        wordDao =db?.wordDao()
    }
    fun getNumber():LiveData<Int>{
       return db!!.wordDao().countNumber()
    }
    suspend fun insertWord(word:WordEntity){
        withContext(ioDispatcher){
            db!!.wordDao().addWord(word)
        }

    }
    suspend fun findWord(word:String):WordEntity{
        var foundWord : WordEntity
        withContext(ioDispatcher){
            foundWord =db!!.wordDao().getWord(word)
        }
        return foundWord
    }
//    fun getId(id:Int):WordEntity{
//        return db!!.wordDao().findId(id)
//    }
    suspend fun delete(word:WordEntity){
        withContext(ioDispatcher){}
        db!!.wordDao().delete(word)
    }
    suspend fun editWord(word:WordEntity){
        withContext(ioDispatcher) {
            db!!.wordDao().edit(word)
        }
    }
    fun getList():LiveData<List<WordEntity>>{
         return db!!.wordDao().getList()
    }
}