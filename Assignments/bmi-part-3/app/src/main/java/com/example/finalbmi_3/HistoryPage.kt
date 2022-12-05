package com.example.finalbmi_3

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

lateinit var bmiHistory: List<String>

@Composable
fun HistoryCompose() {
    // bmiHistory = mutableListOf("25", "22", "35")
    val context = LocalContext.current
    var debugText by remember { mutableStateOf("") }
    debugText = getJsonList(context).toString()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.List,
            contentDescription = "history",
            tint = colorResource(id = R.color.mutedRed),
            modifier = Modifier
                .size(150.dp)
        )
        Text(text = debugText)
    }
}

fun getJsonList(context: Context): List<String> {
    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("bmi-history.json")
            .bufferedReader()
            .use {it.readText()}
    } catch (error: Error) {
        println(error)
        return listOf("not found")
    }
    val listType = object : TypeToken<List<String>>() {}.type
    return Gson().fromJson(jsonString, listType)
}