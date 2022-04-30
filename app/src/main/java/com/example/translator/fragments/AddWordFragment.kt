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
import androidx.core.app.ActivityCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.translator.R
import com.example.translator.database.WordEntity
import com.example.translator.databinding.FragmentAddWordBinding
import com.example.translator.viewModel.AddWordViewModel
import java.io.IOException

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
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
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted){
            Toast.makeText(context, "no permission", Toast.LENGTH_SHORT).show()
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
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)
            record()
        }
        binding.stop.setOnClickListener {
            stopRecording()
        }
        binding.play.setOnClickListener {
            playAudio()
        }
    }

    private fun playAudio() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("playAudioTest", "prepare() failed")
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

    private fun record() {
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
}