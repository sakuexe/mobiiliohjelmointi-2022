package com.example.piirto1

import android.annotation.SuppressLint
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pisteet = mutableListOf<PointF>()
        val piirto = findViewById<MyView>(R.id.myView)
        piirto.setXY(500f, 500f)

        piirto.setOnTouchListener { v, e ->
            piirto.setXY(e.x, e.y)
            pisteet.add(PointF(e.x, e.y))
            piirto.setList(pisteet)
            true
        }
    }
}