package com.example.webscrapetesting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// <String> -> <Ports>
interface GithubService {
    @GET("train-locations/2018-03-01/1")
    fun listRepos(): Call<TrainLocation>?
}

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rata.digitraffic.fi/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GithubService::class.java)
        val mainText = findViewById<TextView>(R.id.mainText)
        val repos: Call<TrainLocation>? = service.listRepos()

        repos?.enqueue(object : Callback<TrainLocation> {

            override fun onResponse(call: Call<TrainLocation>, response: Response<TrainLocation>) {
                if (response.code() == 200) {    // = HTTP OK

                    // tässä portsResponse on tyyppiä Ports
                    val locationResponse = response.body()!!

                    mainText.text = "Train speed: "+ locationResponse[0].speed + "km/h"
                }
            }

            override fun onFailure(call: Call<TrainLocation>, t: Throwable) {
                mainText.text = t.message
            }
        })
    }
}