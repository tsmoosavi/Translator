package com.example.translator

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
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
     val vm: ShowVm by activityViewModels()
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
            var action = ShowAndEditFragmentDirections.actionShowAndEditFragmentToWebFragment(args.id)
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
        var word = vm.findId(args.id)
        binding.editLL.visibility = View.VISIBLE
        binding.editedWord.setText(word.word)
        binding.editedMeaningOfWord.setText(word.meaningOfWord)
        binding.editedExample.setText(word.example)
        binding.editedSynonym.setText(word.synonym)
        binding.editedUrl.setText(word.url)

    }

    private fun deleteWord() {
        var word = vm.findId(args.id)
        vm.delete(word)
        Toast.makeText(context, "this word deleted!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_showAndEditFragment_to_homeFragment)
    }

    private fun initView() {
        var word = vm.findId(args.id)
        binding.word.text = word.word
        binding.meaningOfWord.text = word.meaningOfWord
        binding.example.text = word.example
        binding.synonym.text = word.synonym
        binding.url.text = word.url
    }






}