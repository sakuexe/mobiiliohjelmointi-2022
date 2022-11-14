package com.example.composetestailua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetestailua.ui.theme.ComposeTestailuaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestailuaTheme {
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
    var teksti by remember { mutableStateOf("Click me!") }
    Column(
        modifier = Modifier
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello $name!", modifier = Modifier.padding(2.dp))
        Text(text = "From: SakuK!")
        Button(onClick = {
            // onclick goes here
            teksti = "You clicked me..."
        }) {
            Text(text = teksti)
        }
        TextInput()
    }
}

@Composable
fun TextInput() {
    var inputValue by remember {
        mutableStateOf("")
    }
    TextField(
        value = inputValue,
        onValueChange = {inputValue = it},
        label = {Text("Label")}
    )
    Text(inputValue, color = colorResource(R.color.purple_500))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestailuaTheme {
        Greeting("Android")
    }
}