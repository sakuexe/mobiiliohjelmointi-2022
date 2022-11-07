package com.example.piirto1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val piirto = findViewById<MyView>(R.id.myView)
        piirto.setXY(500f, 500f)

        piirto.setOnTouchListener { v, e ->
            // tee jotain e-muuttujalla
            piirto.setXY(e.x, e.y)
            true
        }
    }
}