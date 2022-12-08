package com.example.finalbmi_3.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.finalbmi_3.R
import com.example.finalbmi_3.datastore.StoreUserData
import com.example.finalbmi_3.isValidInput
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
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
    var showError by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    userHeight = savedHeight.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
            .zIndex(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "BMI Calculator",
            color = colorResource(id = R.color.mutedCream)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = colorResource(id = R.color.lightGreen),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = "Settings",
                fontWeight = FontWeight.Black,
                fontSize = 40.sp,
                style = MaterialTheme.typography.h4.copy(
                    shadow = Shadow(
                        color = colorResource(R.color.shadowGreen),
                        offset = Offset(0f, 10f),
                        blurRadius = 8f,
                    )
                )
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Column {
                Text("Your Height:")
                TextField(
                    value = userHeight,
                    onValueChange = {
                        // showError = false
                        // update textField content
                        userHeight = it
                        // save user's height
                        scope.launch {
                            datastore.saveHeight(userHeight)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone =   {
                            keyboardController?.hide()
                            // if show error is true, return without doing anything else
                            showError = !isValidInput(userHeight)
                            if (showError) return@KeyboardActions
                        }
                    ),
                    isError = showError,
                    singleLine = true,
                )
                if(showError) {
                    Text(text = "Height is invalid")
                }
            }
        }
    }
}
