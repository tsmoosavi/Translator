package com.example.translator

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.translator.database.WordEntity
import com.example.translator.databinding.FragmentShowAndEditBinding
import com.example.translator.viewModel.ShowVm
import java.io.IOException

class ShowAndEditFragment : Fragment() {
     lateinit var binding: FragmentShowAndEditBinding
     val vm: ShowVm by  activityViewModels()
    private var player: MediaPlayer? = null
    var fileName = ""
    private var recorder: MediaRecorder? = null
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
        binding.addFavorite.setOnClickListener {
            checkFavorite()
            var searchWord = args.searchWord
            if (searchWord.isFavorite){
                Toast.makeText(context, "add to favorite", Toast.LENGTH_SHORT).show()
            }else if (!searchWord.isFavorite){
                Toast.makeText(context, "remove from favorite", Toast.LENGTH_SHORT).show()
            }
        }
        binding.record.setOnClickListener {
            record()
        }
        binding.stop.setOnClickListener {
            stopRecording()
        }
        binding.play.setOnClickListener {
            playAudio()
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
        var name = binding.editedWord.text.toString()
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

    private fun playAudio() {
        var searchWord = args.searchWord
        player = MediaPlayer().apply {
            try {
                setDataSource(searchWord.recordFileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("playAudioTest", "prepare() failed")
            }
        }
    }

    private fun checkFavorite() {
        var searchWord = args.searchWord
            searchWord.isFavorite = !searchWord.isFavorite
            var fav = searchWord.isFavorite
            if (fav){
                binding.addFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            } else if (!fav){
                binding.addFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }
    }


    private fun registerEdit() {
        var searchWord = args.searchWord
        var word = binding.editedWord.text.toString()
        var meaning = binding.editedMeaningOfWord.text.toString()
        var example = binding.editedExample.text.toString()
        var syn = binding.editedSynonym.text.toString()
        var link =  binding.editedUrl.text.toString()
        var fileName = "${requireActivity().externalCacheDir?.absolutePath}/audioRecord$word.3gp"
        if (searchWord.isFavorite){
            Toast.makeText(context, "fav", Toast.LENGTH_SHORT).show()
        }else if (!searchWord.isFavorite){
            Toast.makeText(context, "notfav", Toast.LENGTH_SHORT).show()
        }
        vm.edit(WordEntity(searchWord.id,word,meaning,example,syn,link,searchWord.isFavorite,fileName))
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
        if (searchWord.isFavorite){
            binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        } else if (!searchWord.isFavorite){
            binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
        if (searchWord.isFavorite){
            binding.addFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        } else if (!searchWord.isFavorite){
            binding.addFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }

    }






}