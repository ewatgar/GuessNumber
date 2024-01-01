package com.example.guessnumber.ui.configactivity.usecase

import android.graphics.Bitmap.Config
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessnumber.data.model.Info

const val TAG = "ConfigViewModel"

class ConfigViewModel: ViewModel() {
    var name = MutableLiveData<String>()
    var maxTries = MutableLiveData<String>()

    private var state = MutableLiveData<ConfigState>()

    fun getState(): LiveData<ConfigState>{
        return state
    }

    fun checkState() {
        when {
            TextUtils.isEmpty(name.value) -> state.value = ConfigState.NameEmptyError
            TextUtils.isEmpty(maxTries.value) -> state.value = ConfigState.TriesEmptyError
            maxTries.value?.toIntOrNull() == null -> state.value = ConfigState.TriesFormatError
            maxTries.value!!.toInt() <= 0 -> state.value = ConfigState.TriesNotPositiveError
            else -> state.value = ConfigState.Success
        }
    }
}