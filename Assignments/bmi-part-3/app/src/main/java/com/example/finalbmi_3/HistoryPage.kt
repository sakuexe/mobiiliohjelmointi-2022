package com.example.finalbmi_3

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.finalbmi_3.ui.theme.FinalBMI3Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter

@Composable
fun HistoryCompose() {
    // bmiHistory = mutableListOf("25", "22", "35")
    val context = LocalContext.current
    var debugText by remember { mutableStateOf("") }
    // debugText = getJsonList(context).toString()
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
    val bmiList = remember { returnJsonList(context = context) }
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

fun returnJsonList(context: Context): MutableList<String> {
    val gson = Gson()
    val jsonFile = File(context.filesDir, "bmi-history.json")
    val jsonReader = FileReader(jsonFile)
    val type = object : TypeToken<MutableList<String>>() {}.type
    return gson.fromJson(jsonReader, type)
}

fun saveToJson(context: Context, bmiValue: String) {
    // thank you github copilot for this help
    // first let's assign a variable for the bmi history data
    val bmiList: MutableList<String> = try {
        // if there is already a file that returns the data
        // we will use that as a base
        returnJsonList(context)
    } catch (e: Exception) {
        // if there is no file, let's assign the variable as an empty mutable list
        mutableListOf()
    }
    bmiList.add(bmiValue)
    val jsonString = Gson().toJson(bmiList)
    val file = File(context.filesDir, "bmi-history.json")
    val printWriter = PrintWriter(FileWriter(file))
    printWriter.print(jsonString)
    printWriter.close()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FinalBMI3Theme {
        HistoryCompose()
    }
}
