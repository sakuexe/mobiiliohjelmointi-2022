package com.example.intent_testing

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val getText = intent.getStringExtra("teksti")
        val mainText = findViewById<TextView>(R.id.mainText)

        mainText.text = getText

        val button = findViewById<Button>(R.id.doneBtn)
        button.setOnClickListener {
            setResult(107734)
            finish()
        }
    }
}