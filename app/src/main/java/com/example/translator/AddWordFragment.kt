package com.example.translator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.translator.database.WordEntity
import com.example.translator.databinding.FragmentAddWordBinding

class AddWordFragment : Fragment() {
    lateinit var binding: FragmentAddWordBinding
    val vm: AddWordViewModel by viewModels()
    var word = ""
    var meaningOfWord = ""
    var exampleOfWord = ""
    var synonym = ""
    var link = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_word,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener{
            saveData() 
        }
   
    }

    private fun saveData() {
            word = binding.word.text.toString()
            meaningOfWord = binding.meaningOfWord.text.toString()
            exampleOfWord = binding.example.text.toString()
            synonym = binding.synonym.text.toString()
            link = binding.url.text.toString()

            vm.addWord(WordEntity(0,word,meaningOfWord,exampleOfWord,synonym,link))
        Toast.makeText(context, "this word added to dictionary", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addWordFragment_to_homeFragment)
    }
}