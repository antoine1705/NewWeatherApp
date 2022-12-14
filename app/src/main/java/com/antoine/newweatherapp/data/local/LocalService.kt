package com.antoine.newweatherapp.data.local

import com.antoine.newweatherapp.data.local.dao.WeatherDao
import com.antoine.newweatherapp.data.local.entity.CityEntity
import com.antoine.newweatherapp.data.local.entity.WeatherEntity
import com.antoine.newweatherapp.data.response.WeatherResponse

interface LocalService {
    suspend fun getWeatherByCity(cityName: String, date: Int): List<WeatherEntity>

    suspend fun saveWeatherAndCity(cityName: String, weatherResponse: WeatherResponse)
}

class LocalServiceImpl(
    private val weatherDao: WeatherDao
) : LocalService {

    override suspend fun getWeatherByCity(cityName: String, date: Int): List<WeatherEntity> {
        val startDate = System.currentTimeMillis() / 1000
        val endDate = startDate + (24 * 60 * 60 * date)
        return weatherDao.findWeatherByCityName(cityName, startDate, endDate)
    }

    override suspend fun saveWeatherAndCity(cityName: String, weatherResponse: WeatherResponse) {
        weatherResponse.city?.let {
            CityEntity(id = it.id, name = it.name, shortName = cityName, country = it.country)
        }?.let {
            weatherDao.insertCity(it)
        }
        weatherResponse.items?.map { response ->
            WeatherEntity(
                date = response.date,
                tempAvg = response.tempAvg?.let { (it.max + it.min) / 2 }?.toInt(),
                pressure = response.pressure,
                humidity = response.humidity,
                weatherDesc = response.weatherDesc.firstOrNull()?.description,
                cityId = weatherResponse.city?.id
            )
        }?.let {
            weatherDao.insertAllWeather(it)
        }
    }
}