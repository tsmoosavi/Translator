package com.example.translator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.translator.database.Repository

class HomeViewModel(app:Application):AndroidViewModel(app) {
   var wordCountLD: LiveData<Int>

    val repo = Repository()
    init {
        repo.startDb(app.applicationContext)
        wordCountLD = repo.getNumber()
    }

    fun checkWordExistence(word:String):Boolean{
        if (repo.findWord(word) == null){
         return false
        }
        if (repo.findMeaningWord(word) == null){
            return false
        }
        return true
    }
    fun findWord(){

    }


}