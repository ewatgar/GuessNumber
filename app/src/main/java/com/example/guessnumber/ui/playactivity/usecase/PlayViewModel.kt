package com.example.guessnumber.ui.playactivity.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessnumber.data.model.Info
import kotlin.random.Random

class PlayViewModel :ViewModel(){
    var info = MutableLiveData<Info>()
    var guess = MutableLiveData<String>()
    var solution = MutableLiveData<Int>()
    var currentTries = MutableLiveData<Int>()

    private var state = MutableLiveData<PlayState>()

    fun getState(): LiveData<PlayState> {
        return state
    }

    fun generateRandomSolution(){
        solution.value = Random.nextInt(1, 100 + 1)
    }

    fun decrementTries(){
        currentTries.value = currentTries.value!!.toInt() - 1
    }

    fun checkState(){
        when{
            currentTries.value == 0 -> state.value = PlayState.OutOfTriesError
            TextUtils.isEmpty(guess.value) -> state.value = PlayState.GuessEmptyError
            guess.value?.toIntOrNull() == null -> state.value = PlayState.GuessFormatError
            guess.value!!.toInt() <= 0 -> state.value = PlayState.GuessNotPositiveError
            guess.value!!.toInt() != solution.value -> state.value = PlayState.NotCorrectError
            else -> state.value = PlayState.Success
        }
    }
}