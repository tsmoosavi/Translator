package com.example.translator

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.translator.database.Repository
import com.example.translator.database.WordEntity

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
    fun findWord(word:String):WordEntity{
        if (repo.findWord(word) == null){
            return repo.findMeaningWord(word)
        }

        if (repo.findMeaningWord(word) == null){
            return repo.findWord(word)
        }
        return WordEntity(0,"","","","","")
    }

    fun find(word: String):WordEntity{
        if (repo.findWord(word) != null){
            return repo.findWord(word)
        }
        return WordEntity(0,"","","","","")
    }
    fun resultExist(word: String):Boolean{
        if (repo.result(word) == null){
            return false
        }
        return true
    }
    fun result(word: String):WordEntity{
            return repo.result(word)
    }


}