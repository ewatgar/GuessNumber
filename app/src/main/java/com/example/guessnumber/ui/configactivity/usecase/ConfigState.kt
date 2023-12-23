package com.example.guessnumber.ui.configactivity.usecase

sealed class ConfigState {
    data object NameEmptyError: ConfigState()
    data object TriesEmptyError: ConfigState()
    data object TriesFormatError: ConfigState()
    data object TriesNotPositiveError: ConfigState()
    data object Success: ConfigState()

}