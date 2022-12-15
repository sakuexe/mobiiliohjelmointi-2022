# **Mobile Programming 2022**

*a.k.a "Mobiiliohjelmointi 2022"*

By **Saku Karttunen**

#### About

This repository includes exercises from the classes and assignments from HAMK Riihimäki's "Mobile Programming 2022" -course.

- Teacher and course organizer: Toni Laitinen
- Course duration: 24/10/ - 16/12/2022
- Editors used: Android Studio and Visual Studio Code

## Assignments

#### **1) BMI-calculator part 1/3**

The program in the main window asks user's height and weight and with these calculates the weight index of the user
after pressing the "Calculate" button.

The formula for the BMI was Weight(kg)/(Height(m))^2.

```kotlin
  userHeight /= 100
  val bmi = userWeight / (userHeight.pow(2))
  // Round the answer using .format()
  // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/format.html
  header.text = "Your BMI is: %.2f".format(bmi)
```

#### **2) Acceleration Sensor Ball**

- Implement a ball that the user moves around the screen.
- Use the acceleration sensors with the drawing examples in the implementation.
- The ball cannot 'drop' beyond the screen edges.

This was the first app I built using the [Jetpack Compose](https://developer.android.com/jetpack/compose) technology.
It was a great test and I ended up using Compose to build all the next apps (except the second BMI assignment).

```kotlin
  var x by remember { mutableStateOf(540f) }
  var y by remember { mutableStateOf(1024f) }

  val sensorManager = LocalContext.current.getSystemService(SENSOR_SERVICE) as SensorManager
  val mAccelerate : Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
```

*Initalizing the sensorManager with the Acceleration sensor. Also the initial spacing of x and y coordinates*

```kotlin
  val mAccelerateEventListener = object : SensorEventListener {
      override fun onSensorChanged(p0: SensorEvent?) {
        setXY(p0!!.values[0], p0.values[1])
    }
```

*Getting the values of the Acceleration sensor and passing them forward*

#### **3) BMI-calculator part 2/3**

In this assignment the plan was to build on top of the already previously made idea with a couple of new additions.
For example the Height of the user is now being saved in a Settings menu. This menu can be opened from the main page.

```kotlin
  // get sharedPreferences
  val sharedPref = getDefaultSharedPreferences(this)
  if (sharedPref != null) {
    // get value of "signature", if no value, assign "empty"
    userHeight = sharedPref.getString("height_cm", "0.0")!!
    username = sharedPref.getString("username", "Jon Doe")!!
  }
```

*For the height to be remembered even when user leaves and returns to the app, I used SharedPreferences. More specifically the 'DefaultSharedPreferences'*

#### **4) Quiz App**

The assignment was to make a Quiz Application with a four fragment navigation. Each of the fragments needed to have at least three options.
The answer was chosen by clicking the button with the desired answer. The last fragment was meant to show the results of the quiz.

I used Jetpack Compose by Kotlin, so I didn't have a need for fragments, so I made my Quiz App to be a single page application, until
the results would come up, where I would have a different page for the results. Compose made this assignment a lot of fun and
I enjoyed making the layout and the algorithms that counted the right answers.

```kotlin
  data class Prompts (val question: String, val answers: List<String>, val correctAnswer: String)

  var QuizQuestions = listOf<Prompts>(
    Prompts(
        "Who was the first Formula 1 World Champion?",
        listOf(
            "Nikki Lauda",
            "Graham Hill",
            "Qiueseppe Farina",
            "Juan Manuel Fangio"
        ),
        "Qiueseppe Farina"
    )
  )
```

*I made the prompts into an object, so it would be easier to access the needed data (and having it be easier to read)*

```kotlin
  val score = intent.getStringArrayListExtra("userScore")
  val correctAnswers  = intent.getStringArrayListExtra("correctAnswers")
  var finalResult = 0

  for (index in score!!.indices) {
      if (score[index] == correctAnswers!![index]) {
          finalResult += 1
      }
  }
```

*Everytime user chose an answer, the answer got saved and at the end sent with Intent. The correct answers were also sent the same way*
*Then those two just get compared and the final result gets counted.*

If I made this quiz app now, I would've made a couple of things differently, for example:

```json
  {
    question: "Who was the first Formula 1 World Champion?",
    answers: {
      { answer: "Nikki Lauda", score: 0},
      { answer: "Graham Hill", score: 0},
      { answer: "Qiueseppe Farina", score: 1},
      { answer: "Juan Manuel Fangio", score: 0},
    }
  }
```

*I would've made the answers be in this sort of way where they also include a score.*
*This would've made counting the score even easier and more flexible, for example if more points were given than 1 or 0*

#### **5) BMI-calculator part 3/3**

The final assignment, it built on top of the previously learned parts about saving and calculating data in Kotlin.
In this assignment the plan was to make three different pages that would have their own uses. These were:

- Home - this would be the Main page where the user would use the calculator by inserting their weight and pressing "calulate".
- History - This would visualize the previously calculated BMIs.
- Settings - Set the height that is being used in the BMI calculator.

So the most important and complex part of this assignment was how to use the different pages and how to get the data to transfer between them.
For this I used [Android DataStore](https://developer.android.com/topic/libraries/architecture/datastore). This was a good idea, because
it made using the data between the Composables extremely easy.

For the navigation I used the Scaffold element. And for saving the history, I had a JSON file where the BMI values got saved into when calculated.

```kotlin
  class StoreUserData(private val context: Context) {
    // works as another way of storing user data in the same way as shared preferences

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserHeight")
        val USER_HEIGHT_KEY = stringPreferencesKey("user_height")
    }

    // to get the height
    val getHeight: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_HEIGHT_KEY] ?: ""
        }

    // to save the height
    suspend fun saveHeight(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_HEIGHT_KEY] = name
        }
    }
  }
```

*Setting up the DataStore*

```kotlin
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
```

*Saving the value of the bmiValue to JSON*

## **Self Review**

For me it felt like this course helped me a lot to understanding how mobile development happens. And my own work
got better during the course. I am now interested to also try programming some apps in Flutter or ReactNative.

#### **About this course**

This was a great course with a lot of interesting assignments and I apprectiated that I could also do all of the assignments in Compose
since it felt more modern and it seemed to give me a good base knowledge of mobile development that I can use to start trying out
ReactNative and Flutter that also have similiar looking syntax to Compose.

I also found myself enjoying mobile development a lot, so I am looking forward to the future where I can hopefully use what I learned
in a bigger project.

![HAMK-logo](./images/hamk-logo.png)
![Kotlin-logo](./images/kotlin-logo.png)
![Jetpack Compose-logo](./images/compose-logo.png)
