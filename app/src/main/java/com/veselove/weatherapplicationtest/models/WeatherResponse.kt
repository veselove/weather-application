package com.veselove.weatherapplicationtest.models

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    //val list: List<>
    val message: Int
)