package com.example.landscape1

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    var laskuri : Int = 0
    var userAdmin = Person("Antti", 44)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        laskuri = preferences.getInt("key", -1)

        // nullable = if isn't null then do this
        if (savedInstanceState != null) {
            laskuri = savedInstanceState.getInt("laskuri")
            userAdmin = savedInstanceState.getParcelable("antti")!!
            val counterText = findViewById<TextView>(R.id.mainText)
            counterText.text = "Times clicked: ${laskuri.toString()}, Person's age: ${userAdmin.age}"
        }

        findViewById<Button>(R.id.mainButton).setOnClickListener {
            val mainText = findViewById<TextView>(R.id.mainText)
            laskuri++
            preferences
                .edit()
                .putInt("key", laskuri)
                .apply()
            userAdmin.age++
            mainText.text = "Times clicked: $laskuri, User's age: ${userAdmin.age}"
        }
    }

    // tallenna appin tila (state)
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("laskuri", laskuri)
        outState.putParcelable("antti", userAdmin)
        super.onSaveInstanceState(outState)
    }

}