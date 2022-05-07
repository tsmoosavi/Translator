package com.example.translator.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.translator.R
import com.example.translator.database.WordEntity
import com.example.translator.databinding.FragmentAddWordBinding
import com.example.translator.viewModel.AddWordViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class AddWordFragment : Fragment() {
    lateinit var binding: FragmentAddWordBinding
    val vm: AddWordViewModel by viewModels()
    var word = ""
    var meaningOfWord = ""
    var exampleOfWord = ""
    var synonym = ""
    var link = ""
    var fileName = ""
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "you granted this permission", Toast.LENGTH_SHORT).show()
                startRecording()
            } else {
                Toast.makeText(context, "you denied this permission", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_word,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener{
            saveData() 
        }
        binding.record.setOnClickListener {
            requestPermissions()
        }
        binding.stop.setOnClickListener {
            stopRecording()
        }
        binding.play.setOnClickListener {
            playAudio()
        }
   
    }

    private fun saveData() {
            word = binding.word.text.toString()
            meaningOfWord = binding.meaningOfWord.text.toString()
            exampleOfWord = binding.example.text.toString()
            synonym = binding.synonym.text.toString()
            link = binding.url.text.toString()

            vm.addWord(WordEntity(0,word,meaningOfWord,exampleOfWord,synonym,link,false,fileName))
        Toast.makeText(context, "this word added to dictionary", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addWordFragment_to_homeFragment)
    }
    private fun requestPermissions(){
        when {
            //if user already granted the permission
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(context, "you have already granted this permission", Toast.LENGTH_SHORT).show()
                startRecording()
            }
            //if user already denied the permission once
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.RECORD_AUDIO
            ) -> {

                //you can show rational massage in any form you want
                showRationDialog()
                Snackbar.make(binding.record, "we use recorder to save Pronunciation.", Snackbar.LENGTH_LONG).show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO,
                )
            }
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }
    private fun playAudio() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("recordTest", "prepare() failed")
            }
        }
    }


    private fun showRationDialog() {
        val builder= AlertDialog.Builder(requireActivity())
        builder.apply {
            setMessage("we use camera to scan text.")
            setTitle("permission required")
            setPositiveButton("ok"){dialog,which->
                requestPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO,
                )
            }
        }
        builder.create().show()
    }

    private fun startRecording() {
        var name = binding.word.text.toString()
        fileName = "${requireActivity().externalCacheDir?.absolutePath}/audioRecord$name.3gp"
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("recordTest", "prepare() failed")
            }

            start()
        }
    }

}