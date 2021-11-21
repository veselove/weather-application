package com.veselove.weatherapplicationtest.ui

import android.os.Bundle
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
    private val binding get() = _binding!!
    private lateinit var presenter: WeatherContract.Presenter
    private lateinit var model: WeatherContract.Model

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val root: View = binding.root
        model = WeatherModel()
        presenter = WeatherPresenter(this, model, Schedulers.io(), AndroidSchedulers.mainThread())
        presenter.getWeatherData()
        return root
    }

    override fun showWeatherData(forecastModel: ForecastModel) {
        binding.forecastRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ForecastAdapter(forecastModel)
        }
    }

    override fun showErrorMessage(errorMsg: String?) {
        Snackbar.make(requireActivity().findViewById(R.id.container),
            requireContext().resources.getString(R.string.error_message),
            Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.error_try_again_button) {
                presenter.getWeatherData()
            }
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }

}