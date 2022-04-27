package com.example.translator

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.translator.databinding.FragmentWebBinding
import com.example.translator.viewModel.ShowVm

class WebFragment : Fragment() {
    lateinit var binding: FragmentWebBinding
    val vm : ShowVm by activityViewModels()
    val args :WebFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_web,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var word = vm.findId(args.id).word
        binding.webView.loadUrl("https://en.wikipedia.org/wiki/$word")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
    }
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
//            binding.webView.goBack()
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }
//
//    override fun onBackPressed() {
//        if (binding.webView.canGoBack()){
//            binding.webView.goBack()
//        }
//        else
//            super.onBackPressed()
//    }



}