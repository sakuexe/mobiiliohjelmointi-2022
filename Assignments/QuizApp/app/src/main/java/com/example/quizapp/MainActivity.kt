package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Shuffle the order of the questions at the creation
        // no need to shuffle them more than once per game
        QuizQuestions = QuizQuestions.shuffled()
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
        QuizPrompt()
    }
}
@Composable
fun MainTitle(name: String) {
    // The main title on top of the screen
    // Displays: Assignment number and name
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizPrompt() {
    // Initialize the variables, so that the UI will update when they change
    var promptCounter by remember { mutableStateOf(0) }
    var question by remember { mutableStateOf("") }
    val userScore = remember { mutableStateListOf<String>() }
    val correctAnswers = mutableListOf<String>()

    // get correct answers
    // for loop has the same kind of syntax as in javascript
    // except of = in
    for (prompt: Prompts in QuizQuestions) {
        correctAnswers.add(prompt.correctAnswer)
    }

    // get the current prompt
    val currentPrompt = QuizQuestions[promptCounter]
    // initialize the question
    question = currentPrompt.question

    // var answers = remember { mutableStateListOf<String>() }
    var answers = mutableListOf<String>()
    answers.add(currentPrompt.answers[0])
    answers.add(currentPrompt.answers[1])
    answers.add(currentPrompt.answers[2])
    answers.add(currentPrompt.answers[3])

    // shuffle answers, so they aren't in the same place every time
    answers = answers.shuffled() as MutableList<String>

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        Text(text = "Question ${promptCounter + 1}:")
        Text(
            text = "$question",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        // Vertical Grid that is two columns wide
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            content = {
                // Loops through answers and makes a button with the answer as text
                items(answers) { answer ->
                    Button(
                        modifier = Modifier.height(80.dp),
                        // adds the first letter of the string inside of button, to user's score
                        onClick =
                        {
                            userScore.add(answer)
                            // check if there is a next question
                            if(promptCounter < QuizQuestions.size - 1) {
                                promptCounter += 1
                                return@Button
                            }
                            // if it is already last question, go to results screen
                            sendResults(userScore, correctAnswers)
                        },
                    )
                    {
                        Text(text = answer, textAlign = TextAlign.Start)
                    }
                }
            })
    }
}

private fun sendResults(userScore: List<String>, correctAnswers: List<String>) {
    // initialize intent and put userScore into it
    val navigateForward = Intent(this@MainActivity, SecondPage::class.java)
    navigateForward.putStringArrayListExtra("userScore", ArrayList<String>(userScore))
    // Get correctAnswers and also put them in the Intent
    navigateForward.putStringArrayListExtra("correctAnswers", ArrayList<String>(correctAnswers))
    // finally open results page
    startActivity(navigateForward)
}

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
    QuizAppTheme {
        MainCompose()
    }
    }
}