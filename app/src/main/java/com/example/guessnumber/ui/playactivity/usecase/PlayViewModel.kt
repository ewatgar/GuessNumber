package com.example.guessnumber.ui.playactivity.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessnumber.data.model.Info

class PlayViewModel :ViewModel(){
    var guess = MutableLiveData<String>()
    var info = MutableLiveData<Info>()

    var state = MutableLiveData<PlayState>()
}