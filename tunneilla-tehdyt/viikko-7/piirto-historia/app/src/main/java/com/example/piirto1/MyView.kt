package com.example.piirto1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.blue

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // Classin globaalit funktiot
    private var x1 = 100f
    private var y1 = 100f
    var pisteet1: MutableList<PointF> = mutableListOf()

    // piirto funktio
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.RED

        canvas?.drawRect(x1, y1, x1 + 200f, y1 + 300f, paint) // täydennä

        // if(pisteet1 != null)

        // piirrä pisteet1:stä
        for (item in pisteet1) {
            canvas?.drawRect(item.x, item.y, item.x+130f, item.y+130f, paint)
        }
    }

    // funktion perään voi kirjoittaa : [datatype], joka kertoo returnatun arvon
    fun setXY(x: Float, y: Float) {
        x1 = x
        y1 = y
        invalidate()    // pyydetään piirtoa
    }

    fun setList(pisteet: MutableList<PointF>) {
        pisteet1 = pisteet
    }
}