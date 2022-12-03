package com.antoine.newweatherapp.di

import androidx.room.Room
import com.antoine.newweatherapp.R
import com.antoine.newweatherapp.data.local.AppDatabase
import com.antoine.newweatherapp.data.local.LocalService
import com.antoine.newweatherapp.data.local.LocalServiceImpl
import com.antoine.newweatherapp.data.mapper.WeatherRepositoryMapper
import com.antoine.newweatherapp.data.mapper.WeatherRepositoryMapperImpl
import com.antoine.newweatherapp.data.repository.WeatherRepositoryImpl
import com.antoine.newweatherapp.data.service.WeatherForeCastService
import com.antoine.newweatherapp.domain.DispatcherProvider
import com.antoine.newweatherapp.domain.DispatcherProviderImpl
import com.antoine.newweatherapp.domain.repository.WeatherRepository
import com.antoine.newweatherapp.domain.usecase.GetWeatherInfoUseCase
import com.antoine.newweatherapp.presentation.fragment.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModules = module {
    factory<WeatherRepositoryMapper> { WeatherRepositoryMapperImpl() }
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherForeCastService = get(), weatherRepositoryMapper = get(), localService = get()
        )
    }
}
val useCaseModules = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }

    factory {
        GetWeatherInfoUseCase(weatherRepository = get())
    }
}
val networkModules = module {
    single { createOkHttpClient() }
    single {
        createWebService<WeatherForeCastService>(
            okHttpClient = get(), url = androidContext().resources.getString(R.string.base_url)
        )
    }
}

val localModules = module {
    single {
        val database = Room.databaseBuilder(
            get(), AppDatabase::class.java, "weather-examples"
        ).build()
        database.weatherDao()
    }
    single<LocalService> {
        LocalServiceImpl(get())
    }
}

val viewModels = module {
    viewModel {
        WeatherViewModel(
            getWeatherInfoUseCase = get(), weatherMapper = get()
        )
    }
}