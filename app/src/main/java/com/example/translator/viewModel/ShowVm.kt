package com.example.translator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.translator.database.Repository
import com.example.translator.database.WordEntity

class ShowVm(app: Application): AndroidViewModel(app)  {
    val repo = Repository()
    init {
        repo.startDb(app.applicationContext)
    }
    fun findId(id: Int):WordEntity{
        return repo.getId(id)
    }
    fun delete(word:WordEntity){
        repo.delete(word)
    }
}
