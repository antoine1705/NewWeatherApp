package com.antoine.newweatherapp.domain.repository

import com.antoine.newweatherapp.domain.models.WeatherModel
import com.antoine.newweatherapp.domain.usecase.base.AppResult

/**
 * Interface that represents a Repository for getting [WeatherModel] related data.
 */
interface WeatherRepository {
    /**
     * Get a List of [WeatherModel].
     */
    suspend fun getWeatherInfo(cityName: String): AppResult

}