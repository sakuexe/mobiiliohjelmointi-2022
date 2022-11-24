package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.ui.theme.QuizAppTheme

class SecondPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // Background color
                    color = colorResource(id = R.color.darkGray),
                ) {
                    MainCompose()
                }
            }
        }
    }

    @Composable
    fun MainCompose() {
        val score = intent.getStringArrayListExtra("userScore")
        val correctAnswers  = intent.getStringArrayListExtra("correctAnswers")
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp, horizontal = 20.dp),
            // Foreground color
            contentColor = colorResource(id = R.color.mutedWhite),
            // Background color
            color = colorResource(id = R.color.darkGray),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainTitle(name = "Quiz App")
            }
            Results(score, correctAnswers)

            Credits("SakuK")
        }
    }

    @Composable
    fun MainTitle(name: String) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Assignment #3",
                color = colorResource(id = R.color.mutedSalmon)
            )
            Text(
                text = "$name!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black
            )
        }
    }

    @Composable
    private fun Results(score: ArrayList<String>?, correctAnswers: ArrayList<String>?) {
        var finalResult: Int = 0

        for (index in score!!.indices) {
            if (score[index] == correctAnswers!![index]) {
                finalResult += 1
            }
        }

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
                ) {
            Text(text = "Final Results:")
            Text(text = "You got $finalResult/${correctAnswers!!.size}",
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                color = colorResource(id = R.color.mutedSalmon)
            )
            Text(text = "Questions correct. Congratulations!")
            Button(
                border = BorderStroke(2.dp, colorResource(id = R.color.mutedSalmon)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(id = R.color.mutedSalmon),
                    backgroundColor = colorResource(id = R.color.darkGray)
                ),
                modifier = Modifier.padding(top = 10.dp),
                onClick = {
                playAgain()
            }) {
                Text(text = "Play again?")
            }
        }
    }
    private fun playAgain() {
        // Initialize Intent to go back to the main page
        val navigateForward = Intent(this@SecondPage, MainActivity::class.java)
        // this time we do not need to return anything, as the game is restarted

        // Open MainActivity
        startActivity(navigateForward)
    }

    @Composable
    fun Credits(author: String) {
        Column (
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 20.dp),
            ) {
            Text("© $author - 24.11.2022", color = colorResource(id = R.color.secondaryGray))
        }
    }
}