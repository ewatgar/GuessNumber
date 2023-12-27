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
    private var _binding:ActivityPlayBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: PlayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this

        initViewModelInfo()

        binding.bCheck.setOnClickListener {
            Log.d("PlayActivity","guess: "+viewmodel.guess.value)
            Log.d("PlayActivity","currentTries: "+viewmodel.currentTries.value)
            Log.d("PlayActivity","solution: "+viewmodel.solution.value)
            viewmodel.checkState() }
        initTextWatcher()

        binding.bRestart.setOnClickListener {

        }

        viewmodel.getState().observe(this){
            when(it){
                PlayState.NotCorrectError -> setNotCorrectError()
                PlayState.OutOfTriesError -> setOutOfTriesError()
                PlayState.GuessEmptyError -> setGuessEmptyError()
                PlayState.GuessFormatError -> setGuessFormatError()
                PlayState.GuessNotPositiveError -> setGuessNotPositiveError()
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
    private fun onFailure(){
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
        when{
            viewmodel.guess.value!!.toInt() > viewmodel.solution.value!!.toInt() -> binding.tvMessage.text = getString(R.string.error_tv_message_greater_than,viewmodel.guess.value!!.toInt())
            viewmodel.guess.value!!.toInt() < viewmodel.solution.value!!.toInt() -> binding.tvMessage.text = getString(R.string.error_tv_message_less_than,viewmodel.guess.value!!.toInt())
        }
       viewmodel.incrementTries()
    }

    private fun initViewModelInfo() {
        val intentInfo: Info = intent.getSerializableExtra("info") as Info
        with(viewmodel){
            info.value = intentInfo
            //TODO: DUDA: no se porque, tengo que ponerlo como -1 en vez de 0 para que no se deje tener un intento extra en el juego
            currentTries.value = -1
            generateRandomSolution()
        }
    }

    private fun initTextWatcher() {
        with(binding) {
            val twGuess = ErrorTextWatcher(tilGuess) //textWatcher
            edGuess.addTextChangedListener(twGuess)
        }
    }

    private fun navigateToEndPlay(success:Boolean){
        val intent: Intent = Intent(this, EndPlayActivity::class.java)
        val bundle: Bundle = Bundle()
        with(viewmodel){
            bundle.putBoolean("success",success)
            bundle.putInt("currentTries",currentTries.value!!)
            bundle.putInt("solution",solution.value!!)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }
}