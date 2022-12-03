package com.antoine.newweatherapp.domain.usecase

import com.antoine.newweatherapp.domain.repository.WeatherRepository

class GetWeatherInfoUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend fun run(params: String) = weatherRepository.getWeatherInfo(params)
}