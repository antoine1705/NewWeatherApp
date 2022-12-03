package com.antoine.newweatherapp.data.mapper

import com.antoine.newweatherapp.data.local.entity.WeatherEntity
import com.antoine.newweatherapp.data.response.WeatherInfoResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.antoine.newweatherapp.domain.models.ErrorModel
import com.antoine.newweatherapp.domain.models.WeatherModel
import okhttp3.ResponseBody

interface WeatherRepositoryMapper {
    fun toWeatherModel(response: List<WeatherInfoResponse>): List<WeatherModel>
    fun toWeatherModelFromEntity(response: List<WeatherEntity>): List<WeatherModel>
    fun toErrorModel(responseBody: ResponseBody?): ErrorModel?
}

class WeatherRepositoryMapperImpl : WeatherRepositoryMapper {

    override fun toWeatherModel(response: List<WeatherInfoResponse>): List<WeatherModel> {
        return response.map { item ->
            WeatherModel(
                date = item.date ?: 0L,
                humidity = item.humidity ?: 0,
                pressure = item.pressure ?: 0,
                tempAvg = item.tempAvg?.let { (it.max + it.min) / 2 }?.toInt() ?: 0,
                description = item.weatherDesc.firstOrNull()?.description.orEmpty()
            )
        }
    }

    override fun toWeatherModelFromEntity(response: List<WeatherEntity>): List<WeatherModel> {
        return response.map { item ->
            WeatherModel(
                date = item.date ?: 0L,
                humidity = item.humidity ?: 0,
                pressure = item.pressure ?: 0,
                tempAvg = item.tempAvg ?: 0,
                description = item.weatherDesc.orEmpty()
            )
        }
    }

    override fun toErrorModel(responseBody: ResponseBody?): ErrorModel? {
        val gson = Gson()
        val type = object : TypeToken<ErrorModel>() {}.type
        return responseBody?.let { gson.fromJson(it.charStream(), type) as? ErrorModel }
    }
}