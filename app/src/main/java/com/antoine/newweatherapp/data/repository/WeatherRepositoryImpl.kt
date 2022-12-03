package com.antoine.newweatherapp.data.repository

import com.antoine.newweatherapp.data.local.LocalService
import com.antoine.newweatherapp.data.mapper.WeatherRepositoryMapper
import com.antoine.newweatherapp.data.service.WeatherForeCastService
import com.antoine.newweatherapp.domain.repository.WeatherRepository
import com.antoine.newweatherapp.domain.usecase.base.AppResult
import retrofit2.HttpException

class WeatherRepositoryImpl(
    private val weatherForeCastService: WeatherForeCastService,
    private val localService: LocalService,
    private val weatherRepositoryMapper: WeatherRepositoryMapper,
) : WeatherRepository {
    companion object {
        private const val periodDate = 7
    }

    override suspend fun getWeatherInfo(cityName: String): AppResult {
        var result: AppResult = AppResult.GenericError
        try {
            val weatherResponse = weatherForeCastService.getWeatherInformation(cityName)
            val apiItems = weatherResponse.items
            if (!apiItems.isNullOrEmpty()) {
                localService.saveWeatherAndCity(cityName, weatherResponse)
                result = AppResult.Success(weatherRepositoryMapper.toWeatherModel(apiItems))
            }
        } catch (error: Exception) {
            val localItems = localService.getWeatherByCity(cityName, periodDate)
            result = if (localItems.isNotEmpty()) {
                AppResult.Success(weatherRepositoryMapper.toWeatherModelFromEntity(localItems))
            } else if (error is HttpException) {
                val errorBody = error.response()?.errorBody()
                weatherRepositoryMapper.toErrorModel(errorBody)?.let {
                    AppResult.ResponseError(it)
                } ?: AppResult.GenericError
            } else {
                AppResult.NetworkError
            }
        }
        return result
    }
}