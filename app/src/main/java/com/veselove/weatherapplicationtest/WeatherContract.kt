package com.veselove.weatherapplicationtest

import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.Observable

interface WeatherContract {
    interface View {
        fun onInitView()
        fun handleLoaderView(isLoading: Boolean)
        fun showWeatherData(weatherResponse: WeatherResponse)
        fun showErrorMessage(errorMsg: String?)
        fun setLocation(location: String?)
        fun finish()
    }

    interface Presenter {
        fun getWeatherData()
        fun handleWeatherResponse(weatherResponse: WeatherResponse?)
        fun init()
        fun onDestroy()
    }

    interface Model {
        fun loadForecast(): Observable<WeatherResponse>
    }
}