package com.example.weatherapp

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class MainActivity : ComponentActivity() {

    private lateinit var weatherTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTextView = findViewById(R.id.weatherTextView)

        WeatherTask(weatherTextView).execute()
    }

    private class WeatherTask(private val textView: TextView) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg voids: Void): String {
            try {

                val document = Jsoup.connect("https://www.gismeteo.ru/weather-kaliningrad-4225/now/").get()

                val cityElement: Elements = document.select(".page-title > h1")
                val temperatureElement: Elements = document.select(".now-weather > .unit_temperature_c")

                val city: String = cityElement.text()
                val temperature: String = temperatureElement.text()

                return "$city: $temperature"
            } catch (e: IOException) {
                e.printStackTrace()
                return "Error"
            }
        }

        override fun onPostExecute(result: String) {
            textView.text = result
        }
    }
}