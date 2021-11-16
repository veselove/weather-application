package com.veselove.weatherapplicationtest.pojo

data class ForecastModel(
    var location: String,
    var weather: List<ForecastUnit>,
    var weatherDescription: String,
    var humidity: Int,
    var rainVolume: String,
    var pressureGroundLevel: Int,
    var windSpeed: Int,
    var windDirection: String
)