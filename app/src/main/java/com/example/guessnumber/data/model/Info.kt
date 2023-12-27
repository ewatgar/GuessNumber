package com.example.guessnumber.data.model

import android.os.Parcelable
import java.io.Serializable
import kotlin.random.Random

data class Info(val name: String, val maxTries: Int): Serializable