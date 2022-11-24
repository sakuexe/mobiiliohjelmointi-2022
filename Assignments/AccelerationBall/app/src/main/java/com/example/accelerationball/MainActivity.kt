package com.example.accelerationball

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.SENSOR_SERVICE
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.VolumeShaper.Configuration
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.accelerationball.ui.theme.AccelerationBallTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            AccelerationBallTheme {
                // A surface container using the 'background' color from the theme
                MainComposable()
            }
        }
        hideSystemUI()
    }

    // Function to hide NavigationBar
    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,
            window.decorView.findViewById(android.R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun MainComposable() {

    // Choose the orientation to be portrait mode
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(R.color.darkbg)
    ) {
        Ball()
        // Orbs()
        Greeting()
    }
}

@Composable
fun Greeting() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Assignment #2", 
            modifier = Modifier.padding(top = 20.dp),
            color = colorResource(R.color.lightGray),

        )
        Text(text = "Gyro Ball -Game",
            fontSize = 30.sp,
            fontWeight = FontWeight.Black,
            color = colorResource(R.color.purple_200),
            style = MaterialTheme.typography.h4.copy(
                shadow = Shadow(
                    color = colorResource(R.color.black),
                    offset = Offset(0f, 10f),
                    blurRadius = 8f
                )
            )
        )
    }
}

@Composable
fun DebugText() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
            )
    {
        Text(text = "Gravity Sensor:",
            modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 20.dp))
    }
}

@Composable
fun Ball() {
    // redraw composable when variables change
    // default values are floats
    var x by remember { mutableStateOf(540f) }
    var y by remember { mutableStateOf(1024f) }

    val sensorManager = LocalContext.current.getSystemService(SENSOR_SERVICE) as SensorManager
    val mAccelerate : Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val mAccelerateEventListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            setXY(p0!!.values[0], p0.values[1])
        }

        private fun setXY(xPos: Float, yPos: Float) {

            x -= xPos * 10
            y += yPos * 10

            // Right side limit
            if (x > 950f) {
                x = 950f
            }
            // Bottom limit
            if (y > 2080f) {
                y = 2080f
            }

            // Left side limit
            if (x < 130f ) {
                x = 130f
            }
            // Top limit
            if (y < 130f) {
                y = 130f
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

        }
    }

    sensorManager.registerListener(
        // Pass the event listener
        mAccelerateEventListener,
        // set sensor
        mAccelerate,
        // specify sensor manager as delay normal
        SensorManager.SENSOR_DELAY_NORMAL
    )

    val ballColor = colorResource(id = R.color.purple_500)
    // "Draw" the ball into it's canvas
    Canvas(modifier = Modifier
        .fillMaxSize()
    ) {
        drawCircle(
            color = ballColor,
            center = Offset(x, y),
            radius = size.minDimension / 8,
        )
    }

    // if sensorManager needs to be turned down after running
    // sensorManager.unregisterListener(mAccelerateEventListener, mAccelerate)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccelerationBallTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            MainComposable()
        }
    }
}