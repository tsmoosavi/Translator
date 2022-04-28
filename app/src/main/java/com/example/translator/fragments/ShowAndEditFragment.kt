package com.example.translator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.translator.database.WordEntity
import com.example.translator.databinding.FragmentShowAndEditBinding
import com.example.translator.viewModel.ShowVm

class ShowAndEditFragment : Fragment() {
     lateinit var binding: FragmentShowAndEditBinding
     val vm: ShowVm by  activityViewModels()
     val args : ShowAndEditFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_show_and_edit,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        binding.delete.setOnClickListener{
            deleteWord()
        }

        binding.edit.setOnClickListener {
            edit()
        }

        binding.registerEdition.setOnClickListener {
            registerEdit()
        }

        binding.linkLL.setOnClickListener {
            var action = ShowAndEditFragmentDirections.actionShowAndEditFragmentToWebFragment(args.searchWord.url)
          findNavController().navigate(action)
        }
    }



    private fun registerEdit() {
        var id = args.id
        var word = binding.editedWord.text.toString()
        var meaning = binding.editedMeaningOfWord.text.toString()
        var example = binding.editedExample.text.toString()
        var syn = binding.editedSynonym.text.toString()
        var link =  binding.editedUrl.text.toString()
        vm.edit(WordEntity(id,word,meaning,example,syn,link))
        Toast.makeText(context, "edited", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_showAndEditFragment_to_homeFragment)
    }

    private fun edit() {
        var searchWord = args.searchWord
        binding.editLL.visibility = View.VISIBLE
        binding.editedWord.setText( searchWord.word)
        binding.editedMeaningOfWord.setText( searchWord.meaningOfWord)
        binding.editedExample.setText( searchWord.example)
        binding.editedSynonym.setText( searchWord.synonym)
        binding.editedUrl.setText( searchWord.url)

    }

    private fun deleteWord() {
        var searchWord = args.searchWord
        vm.delete( searchWord)
        Toast.makeText(context, "this word deleted!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_showAndEditFragment_to_homeFragment)
    }

    private fun initView() {
        var searchWord = args.searchWord
        binding.word.text =  searchWord.word
        binding.meaningOfWord.text =  searchWord.meaningOfWord
        binding.example.text =  searchWord.example
        binding.synonym.text =  searchWord.synonym
        binding.url.text =  searchWord.url
    }






}