package com.example.finalbmi_3

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter

@Composable
fun HistoryCompose() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
            .zIndex(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "This is your",
            color = colorResource(id = R.color.mutedCream)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = "History",
                tint = colorResource(id = R.color.lightGreen),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = "BMI History",
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
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ShowBMIData()
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ShowBMIData() {
    val context = LocalContext.current
    var bmiList by remember { mutableStateOf(returnJsonList(context)) }
    bmiList = returnJsonList(context = context)
    if (bmiList.size < 1) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No history found",
                fontSize = 20.sp,
            )
        }
        return
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = bmiList, itemContent = { bmiValue ->
            val boxHeight = (bmiValue.toDouble() * 10).dp
            var columnColor: Color = colorResource(id = R.color.darkGray)
            // check the bmi value and color the column rectangle accordingly
            // this makes it easier for the user to understand the meaning of the bmi values
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
    OutlinedButton(
        modifier = Modifier
            .padding(top = 10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = colorResource(id = R.color.mutedCream),
        ),
        border = BorderStroke(2.dp, colorResource(id = R.color.mutedCream)),
        onClick = {
            // remove the data from the bmi-history.json file
            removeJson(context = context)
            // re-assign the new empty list to the bmiList variable
            // so that the history will be drawn again
            bmiList = returnJsonList(context = context)
    }) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete",
            tint = colorResource(id = R.color.mutedCream),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "Clear History")
    }
}

fun returnJsonList(context: Context): MutableList<String> {
    val gson = Gson()
    // try to open the bmi-history file if it exists
    // and then return the contents as a mutable list
    return try {
        val jsonFile = File(context.filesDir, "bmi-history.json")
        val jsonReader = FileReader(jsonFile)
        val type = object : TypeToken<MutableList<String>>() {}.type
        gson.fromJson(jsonReader, type)
    } catch (e: Exception) {
        // if file was not found, return an empty mutable list
        mutableListOf()
    }
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

fun removeJson(context: Context) {
    // remove the file that holds the bmi history data
    // therefore removing the history
    val file = File(context.filesDir, "bmi-history.json")
    file.delete()
}