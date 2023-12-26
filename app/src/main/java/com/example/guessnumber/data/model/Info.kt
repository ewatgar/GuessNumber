package com.example.guessnumber.data.model

import android.os.Parcelable
import java.io.Serializable
import kotlin.random.Random

data class Info(val name: String, val tries: Int, var solution: Int = -1): Serializable{
    fun generateSolution(){
        solution = Random.nextInt(1, 100 + 1)
    }
}
