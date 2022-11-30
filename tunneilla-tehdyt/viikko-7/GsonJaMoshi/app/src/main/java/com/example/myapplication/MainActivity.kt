package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class All {
    var lista = mutableListOf<Person>()
}
class Person(val name:String, var age:Int)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var json:String?=""
        var p = Person("Taylor",44)
        val p2 = Person("Ridge",20)
        var lista = listOf(p, p2)
        var all = All()
        all.lista = lista as MutableList<Person>
/*
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter = moshi.adapter(Person::class.java)
*/
        val gson = Gson()
        //json = jsonAdapter.toJson(p)
        json = gson.toJson(all)   // .toJson(p)

        var json_temp=""
        // lue gson
        val sharedPref = getDefaultSharedPreferences(this)
        if(sharedPref != null) {
            json_temp = sharedPref.getString("json-list", "")!!
            if(json_temp.isNotEmpty()) {
                //p = jsonAdapter.fromJson(json_temp)!!
                val collectionType = object: TypeToken<List<Person>>() {}
                all = gson.fromJson(json_temp, All::class.java) // Person::class.java))
                p.age++
                findViewById<TextView>(R.id.textView).text = all.lista[0].age.toString()
            }
        }



        findViewById<Button>(R.id.button).setOnClickListener {
            //kirjoita gson
            //json = jsonAdapter.toJson(p)
            sharedPref.edit { putString("json-list",json) }
        }
    }
}