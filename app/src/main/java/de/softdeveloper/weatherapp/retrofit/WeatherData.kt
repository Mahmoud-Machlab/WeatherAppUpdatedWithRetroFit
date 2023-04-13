package de.softdeveloper.weatherapp.retrofit

import android.icu.text.IDNA
import androidx.core.app.NotificationCompat.ServiceNotificationBehavior
import com.google.gson.annotations.SerializedName

class WeatherData (
    @SerializedName("name") val city:String,
    @SerializedName("main") val temp:Temp,
    @SerializedName("weather") val info:Array<Info>
)
class Temp (
    val temp:Double
)

class Info (
    val description: String,
    val icon: String
)
