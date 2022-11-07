package com.example.piirto1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // Classin globaalit funktiot
    var x1 = 100f
    var y1 = 100f
    // piirto funktio
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.RED

        canvas?.drawRect(x1, y1, x1 + 200f, y1 + 300f, paint) // täydennä
        canvas?.drawRect(100f, 100f, 200f, 300f, paint) // täydennä
    }

    // asettaa muuttujan arvoja kutsuessa
    // Tehtävä: Aseta xy arvot
    // funktion perään voi kirjoittaa : [datatype], joka kertoo returnatun arvon
    fun setXY(x: Float, y: Float) {
        x1 = x
        y1 = y
        invalidate()    // pyydetään piirtoa
    }
}