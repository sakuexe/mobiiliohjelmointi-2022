package com.example.gyroball

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

// add interface of SensorEventListener to the class
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager : SensorManager
    private var mAccelerate: Sensor? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerate = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val subtitle = findViewById<TextView>(R.id.subtitle)
        subtitle.text = "lux values: ${p0!!.values[0]}, ${p0!!.values[1]}, ${p0!!.values[2]}"

        val board = findViewById<GameBoard>(R.id.gameBoard)
        board.setXY(p0!!.values[0], p0!!.values[1])

        val debugText = findViewById<TextView>(R.id.debugText)
        debugText.text = board.giveCordinates()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mAccelerate?.also { gyro ->
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}