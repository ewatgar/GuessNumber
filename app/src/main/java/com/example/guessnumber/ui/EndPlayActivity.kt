/**
 * EndPlayActivity
 * Tiene la función de enseñar el número de intentos que ha necesito el usuario para adivinar el
 * número, o por el contrario la solución si no ha podido adiviarlo
 */

package com.example.guessnumber.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.guessnumber.R
import com.example.guessnumber.data.model.Info
import com.example.guessnumber.databinding.ActivityEndPlayBinding
import com.example.guessnumber.databinding.ActivityPlayBinding

class EndPlayActivity : AppCompatActivity() {
    private var _binding: ActivityEndPlayBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEndPlayBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initFinalMessage()
    }

    private fun initFinalMessage(){
        val bundle = intent.extras!!
        val success = bundle.getBoolean("success")
        val info = bundle.getSerializable("info") as Info
        val currentTries = bundle.getInt("currentTries")
        val solution = bundle.getInt("solution")
        when(success){
            true -> binding.tvFinalMessage.text = getString(R.string.endplay_tv_final_message_success,info.name,solution,info.maxTries-currentTries)
            false -> binding.tvFinalMessage.text = getString(R.string.endplay_tv_final_message_failure,info.name,solution)
        }
    }
}