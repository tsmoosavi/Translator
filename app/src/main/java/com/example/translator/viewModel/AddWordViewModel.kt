package com.example.translator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.database.Repository
import com.example.translator.database.WordEntity
import kotlinx.coroutines.launch

class AddWordViewModel(app:Application):AndroidViewModel(app) {


    val repo = Repository()
    init {
        repo.startDb(app.applicationContext)
    }
    fun addWord(word: WordEntity){
        viewModelScope.launch {
            repo.insertWord(word)
        }
    }
}