package com.veselove.weatherapplicationtest.network

import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/forecast")
    fun getWeatherInfo(
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("appid")
        apiKey: String,
        @Query("units")
        units: String
    ): Observable<WeatherResponse>

}