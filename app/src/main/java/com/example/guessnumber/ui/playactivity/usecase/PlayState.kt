package com.example.guessnumber.ui.playactivity.usecase

import com.example.guessnumber.ui.configactivity.usecase.ConfigState

sealed class PlayState {
    data object GuessEmptyError: PlayState()
    data object GuessFormatError: PlayState()
    data object NotCorrectError: PlayState()
    data object OutOfTriesError: PlayState()

    data object Success: PlayState()
}