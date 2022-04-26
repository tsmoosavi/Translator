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
import androidx.navigation.fragment.navArgs
import com.example.translator.databinding.FragmentShowAndEditBinding
import com.example.translator.viewModel.ShowVm

class ShowAndEditFragment : Fragment() {
     lateinit var binding: FragmentShowAndEditBinding
     val vm: ShowVm by viewModels()
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