package com.example.finalbmi_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalbmi_3.ui.theme.FinalBMI3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalBMI3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainCompose()
                }
            }
        }
    }
}

@Composable
fun MainCompose() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.darkGray)
    ) {
        Calculate()
    }
}

@Composable
fun Calculate() {
    var mainText by remember { mutableStateOf("Hello World") }
    var counter by remember { mutableStateOf(0) }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Number of times clicked: $counter", color = colorResource(id = R.color.purple_200))
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.purple_200),
                contentColor = colorResource(id = R.color.darkGray)),
            onClick = {
                counter += 1
        }) {
            Text(text = "Calculate")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FinalBMI3Theme {
        MainCompose()
    }
}