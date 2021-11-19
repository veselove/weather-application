package com.veselove.weatherapplicationtest.ui.forecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.veselove.weatherapplicationtest.*
import com.veselove.weatherapplicationtest.databinding.FragmentForecastBinding
import com.veselove.weatherapplicationtest.pojo.ForecastModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForecastFragment : Fragment(), WeatherContract.View {

    private var _binding: FragmentForecastBinding? = null
    lateinit var presenter: WeatherContract.Presenter
    lateinit var model: WeatherContract.Model

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val root: View = binding.root

        model = WeatherModel()
        presenter = WeatherPresenter(this, model, Schedulers.io(), AndroidSchedulers.mainThread())
        presenter.init()
        presenter.getWeatherData()
        init()

        return root
    }

    private fun init() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onInitView() {
        return
    }

    override fun handleLoaderView(isLoading: Boolean) {
        return
    }

    override fun showWeatherData(forecastModel: ForecastModel) {
        Log.i("tempLog", "second fragment works")
        binding.forecastRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ForecastAdapter(forecastModel)
        }
    }

    override fun showErrorMessage(errorMsg: String?) {
        Snackbar.make(requireActivity().findViewById(R.id.container), errorMsg.toString(), Snackbar.LENGTH_INDEFINITE).apply {
            setAction("Try again") {
                presenter.getWeatherData()
            }
            show()
        }
    }

    override fun finish() {
        TODO("Not yet implemented")
    }
}