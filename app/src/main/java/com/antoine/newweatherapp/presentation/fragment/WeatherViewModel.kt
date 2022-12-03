package com.antoine.newweatherapp.presentation.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.antoine.newweatherapp.domain.models.WeatherModel
import com.antoine.newweatherapp.domain.usecase.GetWeatherInfoUseCase
import com.antoine.newweatherapp.presentation.base.BaseViewModel
import com.antoine.newweatherapp.presentation.fragment.adapter.WeatherAdapter
import com.antoine.newweatherapp.presentation.mapper.WeatherMapper
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val weatherMapper: WeatherMapper
) : BaseViewModel() {

    val itemsObs = MutableLiveData<List<WeatherAdapter.AdapterItem>>()

    fun getWeatherForecast(cityName: String) {
        viewModelScope.launch {
            showLoading(true)
            val appResult = getWeatherInfoUseCase.run(cityName)
            showLoading(false)
            appResult.handleResult(::handleSuccess, ::handleFailure)
        }
    }

    private fun handleSuccess(items: List<WeatherModel>) {
        itemsObs.postValue(weatherMapper.toWeatherUI(items))
    }
}