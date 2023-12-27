/**
 * EndPlayActivity
 * Tiene la función de enseñar el número de intentos que ha necesito el usuario para adivinar el
 * número, o por el contrario la solución si no ha podido adiviarlo
 */

package com.example.guessnumber.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.guessnumber.R

class EndPlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_play)
    }
}