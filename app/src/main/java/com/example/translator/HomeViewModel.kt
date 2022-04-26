package com.example.translator

import android.app.Application
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
}