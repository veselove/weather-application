package com.veselove.weatherapplicationtest.network

import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface WeatherAPI {

    @GET("data/2.5/forecast?lat=53.893009&lon=27.567444&appid=f4a137c477b12cff80c2b7c6ac113f4d")
    fun getWeatherInfo(): Observable<WeatherResponse>










//    fun getWeatherInfo(
//        @Query("q")
//        latitude: Double,
//        @Query("page")
//        longitude: Double,
//        @Query("apikey")
//        apiKey: String = API_KEY
//    ): Response<weatherResponse>

}