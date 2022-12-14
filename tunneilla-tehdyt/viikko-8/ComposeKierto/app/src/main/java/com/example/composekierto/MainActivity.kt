package com.example.composekierto

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composekierto.ui.theme.ComposeKiertoTheme
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(var name: String, var age: Int) : Parcelable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeKiertoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainCompose("Android")
                }
            }
        }
    }
}

@Composable
fun MainCompose(name: String) {
    var text by rememberSaveable { mutableStateOf(name) }
    var user by rememberSaveable { mutableStateOf(Person("Sauli", 80)) }
    Column {
        Button(
            onClick = {
                user = Person("Jennifer", 30)
                text = user.name
        }) {
            Text(text = "Press me")
        }
        Text(text = "Hello ${user.name}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeKiertoTheme {
        MainCompose("Android")
    }
}