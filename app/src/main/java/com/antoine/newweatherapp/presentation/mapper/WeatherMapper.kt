package com.antoine.newweatherapp.presentation.mapper

import com.antoine.newweatherapp.domain.models.WeatherModel
import com.antoine.newweatherapp.extensions.DATE_FORMAT_PATTERN_1
import com.antoine.newweatherapp.extensions.formatTimeByPattern
import com.antoine.newweatherapp.presentation.fragment.adapter.WeatherAdapter
import java.util.concurrent.TimeUnit

interface WeatherMapper {
    fun toWeatherUI(items: List<WeatherModel>): List<WeatherAdapter.AdapterItem>
}

class WeatherMapperImpl : WeatherMapper {
    override fun toWeatherUI(items: List<WeatherModel>): List<WeatherAdapter.AdapterItem> {
        if (items.isEmpty()) return listOf(WeatherAdapter.AdapterItem.Empty)
        return items.map {
            val date = TimeUnit.SECONDS.toMillis(it.date).formatTimeByPattern(DATE_FORMAT_PATTERN_1)
            val tempAve = it.tempAvg.toString()
            val pressure = it.pressure.toString()
            val humidity = it.humidity.toString()
            val description = it.description
            WeatherAdapter.AdapterItem.WeatherUI(
                date = date,
                tempAvg = tempAve,
                pressure = pressure,
                humidity = humidity,
                description = description
            )
        }
    }
}