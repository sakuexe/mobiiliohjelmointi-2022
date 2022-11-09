package com.example.landscape1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Person(val name: String, var age: Int) : Parcelable