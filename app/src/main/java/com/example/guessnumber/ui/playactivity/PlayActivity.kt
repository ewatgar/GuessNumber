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

        viewmodel.getState().observe(this){
            when(it){
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
        val intent: Intent = Intent(this, EndPlayActivity::class.java)
        val bundle: Bundle = Bundle()
        with(viewmodel){
            bundle.putSerializable("info", info.value)
            bundle.putInt("currentTries",currentTries.value?:0)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setOutOfTriesError() {
        //TODO
        binding.tvMessage.text = "Sin intentos"
    }

    private fun setGuessEmptyError() {
        with(binding) {
            tilGuess.error = getString(R.string.error_message_til_integer_empty)
            tilGuess.requestFocus()
        }
    }

    private fun setGuessFormatError() {
        with(binding) {
            tilGuess.error = getString(R.string.error_message_til_integer_format)
            tilGuess.requestFocus()
        }
    }

    private fun setGuessNotPositiveError() {
        with(binding) {
            tilGuess.error = getString(R.string.error_message_til_integer_not_positive)
            tilGuess.requestFocus()
        }
    }

    private fun setNotCorrectError() {
        when{
            viewmodel.guess.value!!.toInt() > viewmodel.solution.value!!.toInt() -> binding.tvMessage.text = getString(R.string.error_message_tv_message_greater_than,viewmodel.guess.value!!.toInt())
            viewmodel.guess.value!!.toInt() < viewmodel.solution.value!!.toInt() -> binding.tvMessage.text = getString(R.string.error_message_tv_message_less_than,viewmodel.guess.value!!.toInt())
        }
       viewmodel.decrementTries()
    }

    private fun initViewModelInfo() {
        val intentInfo: Info = intent.getSerializableExtra("info") as Info
        with(viewmodel){
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
}