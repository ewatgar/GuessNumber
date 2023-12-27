/**
 * PlayActivity
 * Tiene la función de preguntarle al usuario el numero que ha pensado, y se mostrará un mensaje que
 * explica si el número es mayor o menor que la solución. Si no ha podido adivinarlo, puede decidir
 * reintentarlo, y en consecuencia generar una nueva solución, o pasar a la siguiente pantalla y
 * ver la solución actual
 */

package com.example.guessnumber.ui.playactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.guessnumber.R

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
    }
}