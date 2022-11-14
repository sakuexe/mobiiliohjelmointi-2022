package com.example.recycledlistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.RecursiveAction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainList = listOf("Ett", "Två", "Tre", "Fyra", "Fem", "Sex", "Sju", "Åtta", "Nio", "Tio")
        val recycleList = findViewById<RecyclerView>(R.id.recycleList)
        val mAdapter = CustomAdapter(mainList)

        recycleList.adapter = mAdapter
        recycleList.layoutManager = LinearLayoutManager(this)
    }
}