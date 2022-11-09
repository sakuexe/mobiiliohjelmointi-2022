package com.example.sensorit1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val mainText = findViewById<TextView>(R.id.mainTitle)
        mainText.text = "Starting text"
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val lux = p0!!.values[0]
        val lux1 = p0!!.values[1]
        val lux2 = p0!!.values[2]
        // teht: kirjoita lux:n arvo TextView:iin
        val mainText = findViewById<TextView>(R.id.mainTitle)
        mainText.text = "Lux values:$lux, $lux1, $lux2"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mLight?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}