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
    lateinit var userHeight: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get sharedPreferences
        val sharedPref = getDefaultSharedPreferences(this)
        if (sharedPref != null) {
            // get value of "signature", if no value, assign "empty"
            userHeight = sharedPref.getString("height_cm", "0.0")!!
            username = sharedPref.getString("username", "Jon Doe")!!
        }

        // initialize help button
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // help button's click functionality
        binding.fab.setOnClickListener { view ->

            val printedText =
                "Your set height is: ${userHeight}cm \n" +
                        "You can set this height from the settings ⚙"

            // makes a pop up at the bottom of the screen and gives it printedText
            Snackbar.make(view, printedText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // main title
        val title = findViewById<TextView>(R.id.mainTitle)
        title.text = "Weight Index -calculator"

        // Calculate button
        val calculateBtn = findViewById<Button>(R.id.calculateButton)
        // Function when clicked
        calculateBtn.setOnClickListener {

            val weigthInput = findViewById<EditText>(R.id.weight_kg).text

            // <[ INPUT GUARD CLAUSES ]>

            // check if userWeight is empty
            if (weigthInput.toString() == "") {
                title.text = "Please insert weight"
                return@setOnClickListener
            }
            // check if userHeight is empty
            if (userHeight == "") {
                title.text = "Please insert height from settings"
                return@setOnClickListener
            }
            // check if userHeight is a number value
            if (userHeight.toDoubleOrNull() == null) {
                title.text = "Please insert a number as height!"
                return@setOnClickListener
            }
            // check if height is a valid value between 20cm and 300cm
            if (userHeight.toDouble() < 20.0 || userHeight.toDouble() > 300.0) {
                title.text = "Please insert valid height from settings"
                return@setOnClickListener
            }

            // calculate BMI:
            // Weight(in KG) % ( Height(in M)^2 )
            var userWeight = weigthInput.toString().toDouble()
            var userHeightMeters = userHeight.toDouble() / 100   // Turns height into meters
            val bmi = userWeight / (userHeightMeters.pow(2))

            // Round the answer using .format()
            // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/format.html
            title.text = "Your BMI is: %.2f".format(bmi)
        }

        // if username is set, give a greeting above the title
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
