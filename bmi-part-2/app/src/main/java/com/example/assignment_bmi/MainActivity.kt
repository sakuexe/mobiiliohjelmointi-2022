package com.example.assignment_bmi

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.example.assignment_bmi.databinding.ActivityMainBinding
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // initialize variables to put sharedPreferences into
    lateinit var username: String
    var userHeight: Double = 150.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPref = getDefaultSharedPreferences(this)
        if (sharedPref != null) {
            // get value of "signature", if no value, assign "empty"
            userHeight = sharedPref.getString("height_cm", "150")!!.toDouble()
            username = sharedPref.getString("username", "Jon Doe")!!
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            // get Default Shared Preferences from the settings page
            val printedText = "Your set height is: ${userHeight}cm \n" +
                    "You can set this height again from the settings"
            // makes a pop up at the bottom of the screen and gives it text
            Snackbar.make(view, printedText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // main title
        val title = findViewById<TextView>(R.id.mainTitle)
        title.text = "Weight Index -calculator"

        // Calculate button
        val calculateBtn = findViewById<Button>(R.id.calculateButton)
        calculateBtn.setOnClickListener {
            // Function when clicked

            val weigthInput = findViewById<EditText>(R.id.weight_kg).text

            if (weigthInput.toString() == "") {
                title.text = "Please insert weight"
                return@setOnClickListener
            }

            var userWeight = weigthInput.toString().toDouble()

            var userHeightMeters = userHeight / 100   // Turns height into meters

            val bmi = userWeight / (userHeightMeters.pow(2))

            // Round the answer using .format()
            // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/format.html
            title.text = "Your BMI is: %.2f".format(bmi)
        }

        val greeting = findViewById<TextView>(R.id.greetingText)
        if (username != "") {
            greeting.text = "Welcome back, $username"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                // open new settings activity
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
