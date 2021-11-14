package com.veselove.weatherapplicationtest

import android.util.Log
import com.google.gson.Gson
import com.veselove.weatherapplicationtest.pojo.WeatherResponse
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOError
import java.io.IOException

class WeatherPresenter(mView: WeatherContract.View, model: WeatherContract.Model,
                       processTread: Scheduler, mainThread: Scheduler
) : WeatherContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var view: WeatherContract.View = mView
    var model: WeatherContract.Model = model
    var processThread: Scheduler = processTread
    var mainThread: Scheduler = mainThread
    /**
     * Initializes the basic UI based on requirement.
     */
    override fun init() {
        view.onInitView()
    }

    override fun getWeatherData() {
        compositeDisposable.add(
            model.loadForecast().subscribeOn(processThread).observeOn(
                mainThread
            ).subscribeWith(object : DisposableObserver<WeatherResponse>() {
                override fun onComplete() {
                    Log.i("tempLog", "RxJava Observer says onComplete")
                }

                override fun onNext(weatherResponse: WeatherResponse) {
                    handleWeatherResponse(weatherResponse)
                    Log.i("tempLog", "RxJava Observer says onNext")
                }

                override fun onError(e: Throwable) {
                    Log.i("tempLog", "RxJava Observer says onError")
                    Log.i("tempLog", "${e.message}")
                }
            }
        ))
    }

    override fun handleWeatherResponse(weatherResponse: WeatherResponse?) {
        if (weatherResponse != null) {
            Log.i("tempLog", "1 " + weatherResponse.list[0].weather[0].description)
            Log.i("tempLog", "2 " + weatherResponse.city.name)
            Log.i("tempLog", "3 " + weatherResponse.city.country)
            Log.i("tempLog", "4 " + weatherResponse.list[0].main.temp.toString())
            Log.i("tempLog", "5 " + weatherResponse.list[0].weather[0].main)
            Log.i("tempLog", "6 " + weatherResponse.list[0].main.humidity.toString())
//            Log.i("tempLog", "7 " + weatherResponse.list[0].rain.`3h`.toString())
            Log.i("tempLog", "8 " + weatherResponse.list[0].main.grnd_level.toString())  //pressure
            Log.i("tempLog", "9 " + weatherResponse.list[0].wind.speed.toString())
            Log.i("tempLog", "10 " + weatherResponse.list[0].wind.deg.toString())

            Log.i("tempLog", "10 " + weatherResponse.list[0].dt_txt)  //we also can use unix format
            Log.i("tempLog", "10 " + weatherResponse.list[0].sys.pod)  //day/night indicator


//            for (i in 0..39) {
//                if (weatherResponse.list[i].weather[0].main == "Rain") {
//                    Log.i("tempLog", "rain 3d $i " + weatherResponse.list[i].rain.`3h`)
//                }
//            }



            view.setLocation("Minsk, BY")
            view.showWeatherData(weatherResponse)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}