package com.example.guessnumber.ui.configactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.guessnumber.ui.PlayActivity
import com.example.guessnumber.databinding.ActivityConfigBinding
import com.example.guessnumber.ui.configactivity.usecase.ConfigState
import com.example.guessnumber.ui.configactivity.usecase.ConfigViewModel

class ConfigActivity : AppCompatActivity() {

    private var _binding: ActivityConfigBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityConfigBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this

        //TODO: DUDA: utilizar viewmodel valor sin layout xml

        binding.bPlay.setOnClickListener { viewmodel.checkState() }
        initTextWatcher()

        //TODO: DUDA: porque "this" y no "viewLifecycleOwner"
        viewmodel.getState().observe(this) {
            when (it) {
                ConfigState.NameEmptyError -> setNameEmptyError()
                ConfigState.TriesEmptyError -> setTriesEmptyError()
                ConfigState.TriesFormatError -> setTriesFormatError()
                ConfigState.TriesNotPositiveError -> setTriesNotPositiveError()
                ConfigState.Success -> onSuccess()
            }
        }
    }

    private fun onSuccess() {
        var intent: Intent = Intent(this, PlayActivity::class.java)
        startActivity(intent)
        /*
        with(viewmodel) {

        }*/

    }

    private fun setNameEmptyError() {
        with(binding) {
            tilName.error = "Introduce tu nombre"
            tilName.requestFocus()
        }
    }

    private fun setTriesEmptyError() {
        with(binding) {
            tilTries.error = "Introduce un número de intentos"
            tilTries.requestFocus()
        }
    }

    private fun setTriesFormatError() {
        with(binding) {
            tilTries.error = "Introduce un número válido"
            tilTries.requestFocus()
        }
    }

    private fun setTriesNotPositiveError() {
        with(binding) {
            tilTries.error = "El número debe ser mayor que 0"
            tilTries.requestFocus()
        }
    }

    private fun initTextWatcher() {
        with(binding) {
            val twName = ConfigTextWatcher(tilName) //textWatcher
            val twTries = ConfigTextWatcher(tilTries)
            edName.addTextChangedListener(twName)
            edTries.addTextChangedListener(twTries)
        }
    }
}
