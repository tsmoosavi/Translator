package com.example.translator.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.translator.database.Repository
import com.example.translator.database.WordEntity

class HomeViewModel(app:Application):AndroidViewModel(app) {
   var wordCountLD: LiveData<Int>
   var wordListLD : LiveData<List<WordEntity>>

    val repo = Repository()
    init {
        repo.startDb(app.applicationContext)
        wordCountLD = repo.getNumber()
        wordListLD = repo.getList()
    }

    fun checkWordExistence (word: String):Boolean{
        if (repo.findWord(word) == null){
            return false
        }
        return true
    }
    fun findWord(word: String):WordEntity{
            return repo.findWord(word)
    }


}