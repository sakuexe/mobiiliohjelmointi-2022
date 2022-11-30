package com.example.finalbmi_3.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finalbmi_3.NavRoutes
import com.example.finalbmi_3.R
import com.example.finalbmi_3.datastore.StoreUserData
import kotlinx.coroutines.launch

@Composable
fun SettingsCompose() {

    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore height
    val datastore = StoreUserData(context)
    // get saved height
    val savedHeight = datastore.getHeight.collectAsState(initial = "not found")

    // local variables
    var userHeight by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    userHeight = savedHeight.value.toString()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            // Align Text and the icon to the same level
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "settings",
                tint = colorResource(id = R.color.mutedRed),
                modifier = Modifier
                    .size(45.dp)
                    .padding(end = 5.dp),
            )
            Text("Settings",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box {
            Column {
                Text("Your Height:")
                TextField(
                    value = userHeight,
                    onValueChange = {
                        // update textField content
                        userHeight = it
                        // save user's height
                        scope.launch {
                            datastore.saveHeight(userHeight)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    maxLines = 1,
                )
            }
        }
        Box {
            Column {
                Text("Username:")
                TextField(
                    value = userHeight,
                    onValueChange = { userName = it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
    }
}
