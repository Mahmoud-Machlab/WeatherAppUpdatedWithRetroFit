package de.softdeveloper.weatherapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import de.softdeveloper.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val isNetworkAvailable:Boolean
    get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = activeNetwork ?: return false
            val actNw = getNetworkCapabilities(networkCapabilities) ?: return false
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)||
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }else{
            activeNetworkInfo?.run {
                type == ConnectivityManager.TYPE_WIFI||
                        type== ConnectivityManager.TYPE_ETHERNET||
                        type == ConnectivityManager.TYPE_MOBILE
            }
        }?:false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowWeather.setOnClickListener {
            if(!isNetworkAvailable){
                Toast.makeText(this, "Kein Netzwerk verfügbar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
    }
}