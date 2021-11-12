package com.veselove.weatherapplicationtest

interface MainContract {
    interface View {
        fun displayWeatherState()
    }

    interface Presenter {
        fun onShareButtonWasClicked()
        fun onDestroy()
    }

    interface Repository {
        fun loadForecast()
    }
}