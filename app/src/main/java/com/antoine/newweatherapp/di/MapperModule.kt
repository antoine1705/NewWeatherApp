package com.antoine.newweatherapp.di

import com.antoine.newweatherapp.presentation.mapper.WeatherMapper
import com.antoine.newweatherapp.presentation.mapper.WeatherMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    factory<WeatherMapper> { WeatherMapperImpl() }
}