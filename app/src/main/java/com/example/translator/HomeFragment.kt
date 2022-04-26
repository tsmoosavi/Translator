package com.example.translator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.translator.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
        lateinit var binding: FragmentHomeBinding
        val vm:HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addWordFragment)
        }
    }

    private fun initView() {
        vm.wordCountLD.observe(viewLifecycleOwner){number ->
            binding.number.text = number.toString()
        }
    }
}