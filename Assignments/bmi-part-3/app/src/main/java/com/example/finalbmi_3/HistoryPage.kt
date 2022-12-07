package com.example.finalbmi_3

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.finalbmi_3.ui.theme.FinalBMI3Theme
import com.example.finalbmi_3.ui.theme.Shapes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

@Composable
fun HistoryCompose() {
    // bmiHistory = mutableListOf("25", "22", "35")
    val context = LocalContext.current
    var debugText by remember { mutableStateOf("") }
    debugText = getJsonList(context).toString()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
            .zIndex(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "This is your",
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.lightGreen)
        )
        Text(
            text = "BMI History",
            fontWeight = FontWeight.Black,
            fontSize = 40.sp,
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ShowBMIData()
    }
}
@Composable
fun ShowBMIData() {
    val context = LocalContext.current
    val bmiList = remember { getJsonList(context = context) }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = bmiList, itemContent = { bmiValue ->
            val boxHeight = (bmiValue.toDouble() * 12).dp
            var columnColor: Color = colorResource(id = R.color.darkGray)
            when (bmiValue.toDouble()) {
                // 1.0 - 18.5
                in 1.0..18.5 -> {
                    columnColor = colorResource(id = R.color.underweightBlue)
                }
                // 24.5 - 29.9
                in 18.5..24.9 -> {
                    columnColor = colorResource(id = R.color.normalGreen)
                }
                // 24.5 - 29.9
                in 24.5..29.9 -> {
                    columnColor = colorResource(id = R.color.overweightYellow)
                }
                // 30 - 34.9
                in 30.0..34.9 -> {
                    columnColor = colorResource(id = R.color.obeseOrange)
                }
                // 35 <
                !in 1.0..34.9 -> {
                    columnColor = colorResource(id = R.color.extremelyObeseRed)
                }
            }
            Box(
                modifier = Modifier
                    .size(height = boxHeight, width = 100.dp)
                    .clip(RectangleShape)
                    .background(columnColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = bmiValue, fontSize = 30.sp,
                    )
                }
            }
        })
    }
}

fun saveJsonList(bmiValue: String, context: Context) {
    val bmiHistoryList = getJsonList(context)
    bmiHistoryList.add(bmiValue)
    val jsonString = Gson().toJson(bmiHistoryList)
    val jsonFile = File("bmi-history.json")
    jsonFile.writeText(jsonString)
}

fun getJsonList(context: Context): MutableList<String> {
    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("bmi-history.json")
            .bufferedReader()
            .use {it.readText()}
    } catch (error: Error) {
        println(error)
        return mutableListOf("not found")
    }
    val listType = object : TypeToken<MutableList<String>>() {}.type
    return Gson().fromJson(jsonString, listType)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FinalBMI3Theme {
        HistoryCompose()
    }
}
