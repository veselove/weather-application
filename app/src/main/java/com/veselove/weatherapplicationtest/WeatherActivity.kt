package com.veselove.weatherapplicationtest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.veselove.weatherapplicationtest.databinding.ActivityMainBinding
import com.veselove.weatherapplicationtest.utils.Coord

class WeatherActivity : AppCompatActivity(), LocationListener{

    private lateinit var binding: ActivityMainBinding
    val REQUEST_LOCATION = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_today, R.id.navigation_forecast
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            setLocation()
    }

    fun setLocation(): List<Double>  {
        var coordinates = listOf(0.0, 0.0)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION
            )
        } else {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            val provider = locationManager.getBestProvider(criteria, false)
            val location = provider?.let { locationManager.getLastKnownLocation(it) }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0f, this)
            if (location != null) {
                coordinates = listOf(location.latitude, location.longitude)
                Coord.lat = location.latitude
                Coord.lon = location.longitude
                Log.i("qwerty", "${Coord.lat}, ${Coord.lon}")
            }
        }
        return coordinates
    }

    override fun onLocationChanged(p0: Location) {
        setLocation()
    }
}