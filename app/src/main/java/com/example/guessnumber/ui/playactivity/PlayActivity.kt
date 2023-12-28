/**
 * PlayActivity
 * Tiene la función de preguntarle al usuario el numero que ha pensado, y se mostrará un mensaje que
 * explica si el número es mayor o menor que la solución. Si no ha podido adivinarlo, puede decidir
 * reintentarlo, y en consecuencia generar una nueva solución, o pasar a la siguiente pantalla y
 * ver la solución actual
 */

package com.example.guessnumber.ui.playactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.guessnumber.R
import com.example.guessnumber.data.model.Info
import com.example.guessnumber.databinding.ActivityPlayBinding
import com.example.guessnumber.ui.EndPlayActivity
import com.example.guessnumber.ui.ErrorTextWatcher
import com.example.guessnumber.ui.playactivity.usecase.PlayState
import com.example.guessnumber.ui.playactivity.usecase.PlayViewModel

class PlayActivity : AppCompatActivity() {
    private var _binding: ActivityPlayBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: PlayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this

        initViewModelInfo()
        debug("init")

        binding.bRestart.setOnClickListener { onRestartGame() }

        binding.bCheck.setOnClickListener {
            viewmodel.checkState()
        }

        initTextWatcher()

        viewmodel.getState().observe(this) {
            when (it) {
                PlayState.OutOfTriesError -> setOutOfTriesError()
                PlayState.GuessEmptyError -> setGuessEmptyError()
                PlayState.GuessFormatError -> setGuessFormatError()
                PlayState.GuessNotPositiveError -> setGuessNotPositiveError()
                PlayState.NotCorrectError -> setNotCorrectError()
                PlayState.Success -> onSuccess()
            }
        }
    }

    private fun onSuccess() {
        navigateToEndPlay(true)
    }

    private fun setOutOfTriesError() {
        binding.tvMessage.text = getString(R.string.error_tv_message_out_of_tries)
        binding.bCheck.text = getString(R.string.play_b_finish_text)
        binding.bCheck.setOnClickListener { onFailure() }
    }

    private fun onFailure() {
        navigateToEndPlay(false)
    }

    private fun setGuessEmptyError() {
        with(binding) {
            tilGuess.error = getString(R.string.error_til_integer_empty)
            tilGuess.requestFocus()
        }
    }

    private fun setGuessFormatError() {
        with(binding) {
            tilGuess.error = getString(R.string.error_til_integer_format)
            tilGuess.requestFocus()
        }
    }

    private fun setGuessNotPositiveError() {
        with(binding) {
            tilGuess.error = getString(R.string.error_til_integer_not_positive)
            tilGuess.requestFocus()
        }
    }

    private fun setNotCorrectError() {
        viewmodel.decrementTries()
        debug("notcorrect")
        when {
            viewmodel.guess.value!!.toInt() > viewmodel.solution.value!!.toInt() -> binding.tvMessage.text =
                getString(R.string.error_tv_message_greater_than, viewmodel.guess.value!!.toInt(), viewmodel.currentTries.value!!)

            viewmodel.guess.value!!.toInt() < viewmodel.solution.value!!.toInt() -> binding.tvMessage.text =
                getString(R.string.error_tv_message_less_than, viewmodel.guess.value!!.toInt(), viewmodel.currentTries.value!!)
        }
    }

    private fun initViewModelInfo() {
        val intentInfo: Info = intent.getSerializableExtra("info") as Info
        with(viewmodel) {
            info.value = intentInfo
            currentTries.value = intentInfo.maxTries
            generateRandomSolution()
        }
    }

    private fun initTextWatcher() {
        with(binding) {
            val twGuess = ErrorTextWatcher(tilGuess) //textWatcher
            edGuess.addTextChangedListener(twGuess)
        }
    }

    private fun navigateToEndPlay(success: Boolean) {
        val intent = Intent(this, EndPlayActivity::class.java)
        val bundle = Bundle()
        with(viewmodel) {
            bundle.putBoolean("success", success)
            bundle.putSerializable("info", info.value)
            bundle.putInt("currentTries", currentTries.value!!)
            bundle.putInt("solution", solution.value!!)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun onRestartGame() {
        with(viewmodel){
            generateRandomSolution()
            currentTries.value = info.value!!.maxTries
        }
        with(binding) {
            bCheck.text = getString(R.string.play_b_check_text)
            tvMessage.text = ""
        }
        debug("restart")
        binding.bCheck.setOnClickListener {
            debug("check after restart")
            viewmodel.checkState()
        }
    }

    private fun debug(string:String){
        Log.d("PlayActivity", "$string -----------------------")
        Log.d("PlayActivity", "currentTries: " + viewmodel.currentTries.value)
        Log.d("PlayActivity", "solution: " + viewmodel.solution.value)
    }
}