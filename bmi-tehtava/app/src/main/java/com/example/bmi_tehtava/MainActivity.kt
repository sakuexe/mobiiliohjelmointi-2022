package com.example.bmi_tehtava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // var vs val: var = variable, val = readonly variable
        // like let and const in JavaScript

        // main header
        val header = findViewById<TextView>(R.id.textmain)
        header.text = "Weight Index -calculator"

        // Calculate button
        val mainButton = findViewById<Button>(R.id.buttonmain)
        mainButton.setOnClickListener {
            // Function when clicked

            var userWeight = findViewById<EditText>(R.id.textWeight).text.toString().toDouble()
            var userHeight = findViewById<EditText>(R.id.textHeight).text.toString().toDouble()

            userHeight /= 100

            val bmi = userWeight / (userHeight.pow(2))
            // Round the answer using .format()
            // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/format.html
            header.text = "Your BMI is: %.2f".format(bmi)
        }
    }
}