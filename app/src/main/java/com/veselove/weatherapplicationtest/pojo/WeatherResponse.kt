package com.veselove.weatherapplicationtest.pojo

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherUnit>,
    val message: Int
)