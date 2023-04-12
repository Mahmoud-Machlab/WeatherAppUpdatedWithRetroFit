package de.softdeveloper.weatherapp

import android.graphics.Bitmap
import android.util.Log
import java.net.URL
import java.text.MessageFormat
import javax.net.ssl.HttpsURLConnection

object WeatherUtil {

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q={0}&lang=de&appid={1}"
    private val TAG = this.javaClass.simpleName

    fun getWeatherData(city:String, key: String): WeatherData {
        var name = ""
        var description = ""
        var icon = ""
        var temp = 0.0
        
        val jsonString = getFromServer(MessageFormat.format(BASE_URL,city,key))
        Log.d(TAG, "getWeatherData: $jsonString")

        return WeatherData(name,description, icon, temp)
    }

    private fun getFromServer(urlString:String): String {
        val url = URL(urlString)
        var jsonString = "{}"
        val connection = url.openConnection() as HttpsURLConnection
        val responseCode = connection.responseCode
        if(responseCode == HttpsURLConnection.HTTP_OK){
            jsonString = connection.inputStream.bufferedReader().use {
                it.readText()
            }
        }
        connection.disconnect()

        return jsonString
    }

    fun getImage(imageName:String):Bitmap?{

        return null
    }



}
