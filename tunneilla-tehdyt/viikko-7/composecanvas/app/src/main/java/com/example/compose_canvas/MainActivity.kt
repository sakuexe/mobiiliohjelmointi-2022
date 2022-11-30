package com.example.compose_canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_canvas.ui.theme.ComposecanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposecanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var black = colorResource(id = R.color.black)
    // initial value is offset.zero
    var ballOffset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(50f) }
    var offsetHistory =  remember { mutableStateListOf<Offset>() }
    Column {
        Canvas (modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // called on tap
                        ballOffset = it
                        size += 50f
                        offsetHistory.add(it)
                    },
                )
            }
        ) {
            drawCircle(black, size, ballOffset)
            for (point in offsetHistory) {
                drawCircle(androidx.compose.ui.graphics.Color.Cyan, 50f, point)
            }
        }
    }
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposecanvasTheme {
        Greeting("Android")
    }
}