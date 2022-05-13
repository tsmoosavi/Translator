package com.example.translator.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.translator.R
import com.example.translator.WordRecyclerAdapter
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
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
        vm.checkWordExistence(binding.searchBox.text.toString()).observe(viewLifecycleOwner){ existence ->
            if (existence){
                vm.findWord(binding.searchBox.text.toString()).observe(viewLifecycleOwner){word ->
                    var action =HomeFragmentDirections.actionHomeFragmentToShowAndEditFragment(word)
                    findNavController().navigate(action)
                }
            }
            else{
                wordFind(requireContext())
            }
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

        vm.wordListLD.observe(viewLifecycleOwner){
            if (it != null){
                var adapter = WordRecyclerAdapter{
                    var action =HomeFragmentDirections.actionHomeFragmentToShowAndEditFragment(it)
                    findNavController().navigate(action)
                }
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        }
    }
}