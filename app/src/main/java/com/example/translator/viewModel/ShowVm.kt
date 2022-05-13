package com.example.translator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.database.Repository
import com.example.translator.database.WordEntity
import kotlinx.coroutines.launch

class ShowVm(app: Application): AndroidViewModel(app)  {
    val repo = Repository()
    init {
        repo.startDb(app.applicationContext)
    }
//    fun findId(id: Int):WordEntity{
//        return repo.getId(id)
//    }
    fun delete(word:WordEntity){
    viewModelScope.launch {
        repo.delete(word)
    }

    }
    fun edit(word: WordEntity){
        viewModelScope.launch {
            repo.editWord(word)
        }
    }
}
