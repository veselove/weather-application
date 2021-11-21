package com.veselove.weatherapplicationtest

import com.veselove.weatherapplicationtest.pojo.ForecastModel
import com.veselove.weatherapplicationtest.pojo.ForecastUnit
import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import com.veselove.weatherapplicationtest.utils.Constants.Companion.API_KEY
import com.veselove.weatherapplicationtest.utils.Constants.Companion.UNITS_METRIC
import com.veselove.weatherapplicationtest.utils.Coordinates
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.util.*

class WeatherPresenter(mView: WeatherContract.View, model: WeatherContract.Model,
                       processTread: Scheduler, mainThread: Scheduler
) : WeatherContract.Presenter{

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var view: WeatherContract.View = mView
    var model: WeatherContract.Model = model
    var processThread: Scheduler = processTread
    var mainThread: Scheduler = mainThread
    var forecastForShare = ""

    override fun getWeatherData() {
        compositeDisposable.add(
            model.loadForecast(Coordinates.lat, Coordinates.lon, API_KEY, UNITS_METRIC).subscribeOn(processThread).observeOn(
                mainThread
            ).subscribeWith(object : DisposableObserver<WeatherResponse>() {
                override fun onComplete() {
                }

                override fun onNext(weatherResponse: WeatherResponse) {
                    handleWeatherResponse(weatherResponse)
                }

                override fun onError(e: Throwable) {
                    view.showErrorMessage(R.string.error_message.toString())
                }
            }
        ))
    }

    override fun handleWeatherResponse(weatherResponse: WeatherResponse) {

            val location = weatherResponse.city.name + ", " + weatherResponse.city.country
            val weatherDescription = weatherResponse.list[0].weather[0].main
            val humidity = weatherResponse.list[0].main.humidity
            val pressureGroundLevel = weatherResponse.list[0].main.grnd_level
            val windSpeed = weatherResponse.list[0].wind.speed.toInt()
            val rainVolume = if (weatherResponse.list[0].weather[0].main ==
                "Rain") { weatherResponse.list[0].rain.`3h`.toString()
            } else {"0.0"}
            val windDirection = azimuthToAbbreviationConverter(weatherResponse.list[0].wind.deg)
            val weatherMutableList = mutableListOf<ForecastUnit>()
            for (i in weatherResponse.list.indices) {
                weatherMutableList.add(ForecastUnit())
            }

            for (n in weatherResponse.list.indices) {
                weatherMutableList[n].weatherIcon = iconPicker(weatherResponse.list[n].weather[0].icon)
                weatherMutableList[n].dayOfWeek = unixToDayOfWeekConverter(weatherResponse.list[n].dt)
                weatherMutableList[n].time = unixToTimeConverter(weatherResponse.list[n].dt)
                weatherMutableList[n].temperature = weatherResponse.list[n].main.temp.toInt().toString() + "°"
                weatherMutableList[n].weatherDescription = weatherResponse.list[n].weather[0].description
            }

            val immutableWeatherList = Collections.unmodifiableList(weatherMutableList)
            val forecastModel = ForecastModel(location,
                immutableWeatherList,
                weatherDescription,
                humidity,
                rainVolume,
                pressureGroundLevel,
                windSpeed,
                windDirection)

            view.showWeatherData(forecastModel)
            createForecastForShare(forecastModel)
    }

    private fun azimuthToAbbreviationConverter(azimuth: Int): String {
        return when (azimuth) {
            in 0..22 -> "N"
            in 23..67 -> "NE"
            in 68..112 -> "E"
            in 113..157 -> "SE"
            in 158..202 -> "S"
            in 203..247 -> "SW"
            in 248..292 -> "W"
            in 293..337 -> "NW"
            in 338..360 -> "N"
            else -> "N/A"
        }
    }

    private fun iconPicker(icon: String): Int {
        return when (icon) {
            "01d" -> R.mipmap.clear_sky_day
            "01n" -> R.mipmap.clear_sky_night
            "02d" -> R.mipmap.few_clouds_day
            "02n" -> R.mipmap.few_clouds_night
            "03d" -> R.mipmap.scattered_clouds_day
            "03n" -> R.mipmap.scattered_clouds_night
            "04d" -> R.mipmap.broken_clouds_day
            "04n" -> R.mipmap.broken_clouds_night
            "09d" -> R.mipmap.shower_rain_day
            "09n" -> R.mipmap.shower_rain_night
            "10d" -> R.mipmap.rain_day
            "10n" -> R.mipmap.rain_night
            "11d" -> R.mipmap.thunderstorm_day
            "11n" -> R.mipmap.thunderstorm_night
            "13d" -> R.mipmap.snow_day
            "13n" -> R.mipmap.snow_night
            "50d" -> R.mipmap.mist_day
            "50n" -> R.mipmap.mist_night
            else -> R.mipmap.error
        }
    }

    private fun unixToDayOfWeekConverter(time: Int): String {
        val date = Date(time.toLong() * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "N/A"
        }
    }

    private fun unixToTimeConverter(time: Int): String {
        val date = Date(time.toLong() * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY).toString() + ":00"
    }

    private fun createForecastForShare(model: ForecastModel) {
        val sb = StringBuilder()
        sb.append("5 Days Weather Forecast:\n")
        for (n in model.weather.indices) {
            sb.append(model.weather[n].dayOfWeek + " " + model.weather[n].time +
                    ", temperature: " + model.weather[n].temperature + "C, " +
                    model.weather[n].weatherDescription + ";\n")
        }
        sb.setLength(sb.length - 2)
        sb.append(".")
        forecastForShare = sb.toString()
    }

    override fun shareForecast(): String {
        return forecastForShare
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}