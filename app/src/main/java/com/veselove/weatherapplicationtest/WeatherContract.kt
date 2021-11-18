package com.veselove.weatherapplicationtest
git add
import com.veselove.weatherapplicationtest.pojo.ForecastModel
import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.Observable

interface WeatherContract {
    interface View {
        fun onInitView()
        fun handleLoaderView(isLoading: Boolean)
        fun showWeatherData(forecastModel: ForecastModel)
        fun showErrorMessage(errorMsg: String?)
        fun finish()
    }

    interface Presenter {
        fun getWeatherData()
        fun handleWeatherResponse(weatherResponse: WeatherResponse)
        fun init()
        fun onDestroy()
    }

    interface Model {
        fun loadForecast(latitude: Double, longitude: Double, apiKey: String, units: String): Observable<WeatherResponse>
    }
}