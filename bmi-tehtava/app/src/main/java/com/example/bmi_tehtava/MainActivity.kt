package com.example.bmi_tehtava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import org.w3c.dom.Text
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

            val BMI = userWeight / (userHeight.pow(2))
            header.text = "Your Weight: $userWeight, Your Height: $userHeight, so BMI is: $BMI"
        }
    }
}