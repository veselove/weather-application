package com.veselove.weatherapplicationtest

import com.veselove.weatherapplicationtest.pojo.ForecastModel
import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.Observable

interface WeatherContract {
    interface View {
        fun showWeatherData(forecastModel: ForecastModel)
        fun showErrorMessage(errorMsg: String?)
    }

    interface Presenter {
        fun getWeatherData()
        fun handleWeatherResponse(weatherResponse: WeatherResponse)
        fun shareForecast(): String
        fun onDestroy()
    }

    interface Model {
        fun loadForecast(latitude: Double, longitude: Double, apiKey: String, units: String):
                Observable<WeatherResponse>
    }
}
