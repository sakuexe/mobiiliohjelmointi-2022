package com.example.gyroball

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mainAct : MainActivity? = null

    // Classin globaalit funktiot
    var x1 : Float = 500f
    var y1 : Float = 500f

    // piirto funktio
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.MAGENTA

        canvas?.drawCircle(x1, y1, 100f, paint)
    }

    // asettaa muuttujan arvoja kutsuessa
    // Tehtävä: Aseta xy arvot
    // funktion perään voi kirjoittaa : [datatype], joka kertoo returnatun arvon
    fun setXY(x: Float, y: Float) {
        var xAcceleration = x * 10
        var yAcceleration = y * 10

        if (x1 - xAcceleration < 1080f && x1 - xAcceleration > 0f) {
            x1 -= x * 10
        }
        if (y1 + yAcceleration < 2054f && y1 + yAcceleration > 0f) {
            y1 += y * 10
        }
        invalidate()    // pyydetään piirtoa
    }

    fun giveCordinates() : String {
        return "x: $x1, y: $y1"
    }
}