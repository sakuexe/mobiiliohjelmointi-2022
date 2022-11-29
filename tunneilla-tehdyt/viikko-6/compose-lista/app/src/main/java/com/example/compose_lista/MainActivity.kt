package com.example.compose_lista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_lista.ui.theme.ComposelistaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myList = listOf("first", "second", "third", "fourth", "fifth")
        val group = listOf(Person("Markku", 40), Person("Minttu", 20), Person("Alfred", 75))
        setContent {
            ComposelistaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Compose-lista", myList, group)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, givenList: List<String>, group: List<Person>) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Tuntitehtävä: $name")
        LazyColumn(content = {
            items(group) { message ->
                Text(text = "${message.name}, ${message.age}",
                modifier = Modifier.padding(vertical = 10.dp))
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val myList = listOf("first", "second", "third", "fourth", "fifth")
    val group = listOf(Person("Markku", 40), Person("Minttu", 20), Person("Alfred", 75))
    ComposelistaTheme {
        Greeting("Compose-lista", myList, group)
    }
}

data class Person(var name: String, var age: Int)