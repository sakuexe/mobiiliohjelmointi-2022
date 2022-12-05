package com.example.compose_scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_scaffolding.ui.theme.ComposescaffoldingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposescaffoldingTheme {
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
    ScaffoldDemo()
}

@Composable
fun ScaffoldDemo() {
    val materialBlue700= Color(0xFF1976D2)
    // val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        // scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = {Text("TopAppBar")},backgroundColor = materialBlue700,
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Favorite, "Favorite")
                }
                IconButton(onClick = { showMenu = !showMenu}) {
                    Icon(Icons.Default.MoreVert, "MoreVert")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false })
                {
                    DropdownMenuItem(onClick = {
                        // TODO
                    }) {
                        Icon(Icons.Filled.Refresh, "Settings")
                    }
                    DropdownMenuItem(onClick = {
                        // TODO
                    }) {
                        Icon(Icons.Filled.Call, "")
                    }
                }
            }
        )  },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = {}){
            Text("X")
        } },
        // drawerContent = { Text(text = "drawerContent") },
        content = { Text("BodyContent") },
        // bottomBar = { BottomAppBar(backgroundColor = materialBlue700) { Text("BottomAppBar") } }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposescaffoldingTheme {
        MainCompose()
    }
}