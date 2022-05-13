package com.example.translator.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.translator.database.Repository
import com.example.translator.database.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(app:Application):AndroidViewModel(app) {
   var wordCountLD: LiveData<Int>
   var wordListLD : LiveData<List<WordEntity>>

    val repo = Repository()
    init {
        repo.startDb(app.applicationContext)
        wordCountLD = repo.getNumber()
        wordListLD = repo.getList()
    }

    fun checkWordExistence (word: String):LiveData<Boolean>{
        var isExist = MutableLiveData<Boolean>()
        viewModelScope.launch {
            if (repo.findWord(word) == null){
                var existence =  false
                isExist.value = existence
            }else{
                isExist.value = true
            }
        }
        return isExist
    }
    fun findWord(word: String):LiveData<WordEntity>{
        var foundWordLD = MutableLiveData<WordEntity>()
        viewModelScope.launch{
            var foundWord =  repo.findWord(word)
            foundWordLD.value = foundWord
        }
        return foundWordLD
    }


}