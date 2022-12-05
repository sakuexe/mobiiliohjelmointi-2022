package com.example.compose_listaus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_listaus.ui.theme.ComposelistausTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposelistausTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainCompose(true)
                }
            }
        }
    }
}


@Composable
fun MainCompose(givenValue: Boolean) {
    // val usedList = mutableListOf("A", "B", "C", "D")
    val currentList = remember { mutableStateListOf("First", "Second", "Third", "Fourth") }
    var currentText by remember { mutableStateOf("") }
    val currentContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // do something
            Text(
                text = currentText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 10.dp),
            )
            Button(onClick = {
                currentList.add("the last line")
                currentText += "!"
                currentList[0] += "?"
            }) {
                Text(text = "press me")
            }
            Text(
                text = currentText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
        if (givenValue) {
            // elements can be put inside of conditionals
            Text("True")
        }
        LazyColumn() {
            items(items = currentList) { listItem ->
                Text(text = listItem, modifier = Modifier.selectable(false){
                    // do something when clicked
                    Toast.makeText(currentContext, listItem, Toast.LENGTH_LONG).show()
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposelistausTheme {
        MainCompose(true)
    }
}