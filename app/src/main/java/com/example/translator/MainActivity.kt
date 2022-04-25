package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.translator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.hide()
        binding.splashScreen.alpha = 0f
        binding.splashScreen.animate().setDuration(6000).alpha(1f).withEndAction{
            binding.homeFragment.visibility = View.VISIBLE
            binding.splashScreen.visibility = View.GONE
            overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.fade_out)
            supportActionBar?.show()
        }
    }
}