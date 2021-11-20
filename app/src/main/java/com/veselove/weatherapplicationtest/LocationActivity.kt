package com.veselove.weatherapplicationtest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.veselove.weatherapplicationtest.utils.Coord

class LocationActivity : AppCompatActivity(), LocationListener {

    private val locationPermissionCode = 2
    private var isFirstRequest = true
    private val TAG = "LocationActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        supportActionBar?.hide()

        if (!hasLocationPermission()) {
            showLocationPermissionAlertDialog()
        } else setLocation()

    }

    private fun hasLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                )
    }

    private fun showLocationPermissionAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(R.string.dialog_message)
        builder.setPositiveButton(R.string.dialog_ok) { _, _ ->
            setLocation()
        }
        builder.show()
    }

    private fun setLocation() {
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
                locationPermissionCode
            )
        } else {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            val provider = locationManager.getBestProvider(criteria, false)
            val location = provider?.let { locationManager.getLastKnownLocation(it) }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
            if (location != null) {
                Coord.lat = location.latitude
                Coord.lon = location.longitude
            }
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    isFirstRequest) {
                isFirstRequest = false
                setLocation()
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        Log.i(TAG, location.toString())
    }

}