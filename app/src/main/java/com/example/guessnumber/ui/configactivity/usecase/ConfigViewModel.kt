package com.example.guessnumber.ui.configactivity.usecase

import android.graphics.Bitmap.Config
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessnumber.data.model.Info

const val TAG = "ConfigViewModel"

class ConfigViewModel: ViewModel() {
    /* TODO: DUDA: no puedo usar info porque double deberia ser string para poder verificarse
        si está vacío o si es double.Podría cambiar el dataclass a tries: String
    */
    //var info = MutableLiveData<Info>()

    var name = MutableLiveData<String>()
    var tries = MutableLiveData<String>()

    private var state = MutableLiveData<ConfigState>()

    fun getState(): LiveData<ConfigState>{
        return state
    }

    fun checkState() {
        when {
            TextUtils.isEmpty(name.value) -> state.value = ConfigState.NameEmptyError
            TextUtils.isEmpty(tries.value) -> state.value = ConfigState.TriesEmptyError
            tries.value?.toIntOrNull() == null -> state.value = ConfigState.TriesFormatError
            tries.value!!.toInt() <= 0 -> state.value = ConfigState.TriesNotPositiveError
            else -> state.value = ConfigState.Success
        }
    }
}