package com.veselove.weatherapplicationtest.pojo

data class ForecastUnit(
    var weatherIcon: Int,
    var dayOfWeek: String,
    var time: String,
    var temperature: String,
    var weatherDescription: String
    ) {
    constructor() : this(0,
        "",
        "",
        "",
        "")
}