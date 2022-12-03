package com.antoine.newweatherapp.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.antoine.newweatherapp.R
import com.antoine.newweatherapp.databinding.FragmentWeatherBinding
import com.antoine.newweatherapp.extensions.viewBinding
import com.antoine.newweatherapp.presentation.fragment.adapter.WeatherAdapter
import com.antoine.newweatherapp.presentation.models.AppError
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModel()

    private val binding by viewBinding(FragmentWeatherBinding::bind)

    private lateinit var weatherAdapter: WeatherAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() = with(binding) {
        if (!::weatherAdapter.isInitialized) {
            weatherAdapter = WeatherAdapter()
        }
        rvWeatherInfo.adapter = weatherAdapter
        ContextCompat.getDrawable(requireContext(), R.drawable.shape_line_divider)?.let {
            val decorator = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            decorator.setDrawable(it)
            rvWeatherInfo.addItemDecoration(decorator)
        }
        btnWeather.setOnClickListener {
            if (!edtLocation.text.isNullOrEmpty()) {
                viewModel.getWeatherForecast(edtLocation.text.toString())
            }
        }
    }

    private fun setupVM() = with(viewModel) {
        itemsObs.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it)
        }
        errorObs.observe(viewLifecycleOwner) {
            when (it) {
                is AppError.GenericError -> {
                    weatherAdapter.submitList(listOf(WeatherAdapter.AdapterItem.Empty))
                }
                is AppError.ResponseError -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                else -> Toast.makeText(
                    requireContext(), getString(R.string.error_network), Toast.LENGTH_SHORT
                ).show()
            }
        }
        loadingObs.observe(viewLifecycleOwner) {

        }
    }

}