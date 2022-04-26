package com.example.translator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.translator.databinding.FragmentHomeBinding
import com.example.translator.viewModel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() {
        lateinit var binding: FragmentHomeBinding
        val vm: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addWordFragment)
        }
        binding.searchButton.setOnClickListener {
            showWord()
        }
    }

    private fun showWord() {
        if (vm.checkWordExistence(binding.searchBox.text.toString())){
            Toast.makeText(context, vm.findWord(binding.searchBox.text.toString()).id.toString(), Toast.LENGTH_SHORT).show()
        }
        else{
            wordFind(requireContext())
        }
    }

    private fun wordFind(context: Context){
        MaterialAlertDialogBuilder(context)
            .setTitle("Oops!")
            .setMessage("there is no such a word!")
            .show()
    }

    private fun initView() {
        vm.wordCountLD.observe(viewLifecycleOwner){number ->
            binding.number.text = number.toString()
        }
    }
}