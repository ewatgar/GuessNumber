/**
 * ConfigActivity
 * Tiene la función de preguntarle al usuario información antes de empezar el juego, en concreto,
 * se pregunta por su nombre y el número de intentos que tiene para adivinar un número aleatorio
 * entre el 1 y el 100
 */

package com.example.guessnumber.ui.configactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.guessnumber.R
import com.example.guessnumber.data.model.Info
import com.example.guessnumber.ui.playactivity.PlayActivity
import com.example.guessnumber.databinding.ActivityConfigBinding
import com.example.guessnumber.ui.ErrorTextWatcher
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
        intent.putExtra("info", Info(viewmodel.name.value!!, viewmodel.maxTries.value!!.toInt()))
        startActivity(intent)
    }

    private fun setNameEmptyError() {
        with(binding) {
            tilName.error = getString(R.string.error_til_name_empty)
            tilName.requestFocus()
        }
    }

    private fun setTriesEmptyError() {
        with(binding) {
            tilTries.error = getString(R.string.error_til_integer_empty)
            tilTries.requestFocus()
        }
    }

    private fun setTriesFormatError() {
        with(binding) {
            tilTries.error = getString(R.string.error_til_integer_format)
            tilTries.requestFocus()
        }
    }

    private fun setTriesNotPositiveError() {
        with(binding) {
            tilTries.error = getString(R.string.error_til_integer_not_positive)
            tilTries.requestFocus()
        }
    }

    private fun initTextWatcher() {
        with(binding) {
            val twName = ErrorTextWatcher(tilName) //textWatcher
            val twTries = ErrorTextWatcher(tilTries)
            edName.addTextChangedListener(twName)
            edTries.addTextChangedListener(twTries)
        }
    }
}
