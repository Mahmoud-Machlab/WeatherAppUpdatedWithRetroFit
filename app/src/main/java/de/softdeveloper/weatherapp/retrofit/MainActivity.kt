package de.softdeveloper.weatherapp.retrofit

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import de.softdeveloper.weatherapp.R
import de.softdeveloper.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val BASE_URL_STRING = "https://api.openweathermap.org/data/2.5/"
    val IMAGE_URL_STRING = "https://openweathermap.org/img/w/"
    val FILE_EXT = ".png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var weatherDataDef : Deferred<WeatherData>
        var weatherData: WeatherData
        var image: Bitmap
        var imageCall: Call<ResponseBody>

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_STRING)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.btnShowWeather.setOnClickListener {
            val apiService= retrofit.create(WeatherEndpointInterface::class.java)
            val city = binding.etCity.text.toString()
            val appid = getString(R.string.api_key)
            val call = apiService.getWeatherData(city, id = appid)

            call.enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                   runBlocking {
                       weatherDataDef = async { response.body()!!}
                       weatherData = weatherDataDef.await()

                       binding.tvDesc.text= weatherData.info[0].description
                       binding.tvTemp.text= weatherData.temp.temp.minus(273.15).toInt().toString()
                   }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fehler beim Abruf der Daten", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity", "Lesefehler ",t )
                }
            })
        }
    }
}