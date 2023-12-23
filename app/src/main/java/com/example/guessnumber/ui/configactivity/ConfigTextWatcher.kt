package com.example.guessnumber.ui.configactivity

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

//TODO: DUDA: porque se escribe private val
class ConfigTextWatcher(private val til:TextInputLayout):TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //no se implementa
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //no se implementa
    }

    override fun afterTextChanged(s: Editable?) {
        til.error = null
    }
}