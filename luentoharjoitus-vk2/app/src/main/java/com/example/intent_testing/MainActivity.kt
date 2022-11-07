package com.example.intent_testing

import android.annotation.SuppressLint
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Tee jotain it.data on vastaanotettu Intent ja it.resultCode paluuarvo
        val ht = findViewById<TextView>(R.id.helloText)
        ht.text = it.resultCode.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get the button elements by ID
        val openBtn = findViewById<Button>(R.id.button)
        val systemBtn = findViewById<Button>(R.id.sysBtn)

        openBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            intent.putExtra("teksti", "teksti koodista toiselta sivulta!")
            startActivity(intent)
            startForResult.launch(intent)
        }

        systemBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            startActivity(intent)
        }
    }
}