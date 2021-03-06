package com.veselove.weatherapplicationtest

import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import com.veselove.weatherapplicationtest.network.WeatherRetrofitInstance
import io.reactivex.Observable

class WeatherModel() : WeatherContract.Model {

    override fun loadForecast(latitude: Double, longitude: Double, apiKey: String, units: String):
            Observable<WeatherResponse> =
        WeatherRetrofitInstance.api.getWeatherInfo(latitude, longitude, apiKey, units)

}