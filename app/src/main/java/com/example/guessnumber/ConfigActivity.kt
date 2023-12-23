package com.example.guessnumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.guessnumber.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {

    private var _binding: ActivityConfigBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityConfigBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        var intent: Intent = Intent(this, PlayActivity::class.java)
    }
}