@file:OptIn(ExperimentalCoroutinesApi::class)

package com.antoine.newweatherapp.fragment

import com.antoine.newweatherapp.domain.models.WeatherModel
import com.antoine.newweatherapp.domain.usecase.GetWeatherInfoUseCase
import com.antoine.newweatherapp.domain.usecase.base.AppResult
import com.antoine.newweatherapp.presentation.fragment.WeatherViewModel
import com.antoine.newweatherapp.presentation.fragment.adapter.WeatherAdapter
import com.antoine.newweatherapp.presentation.mapper.WeatherMapper
import com.antoine.newweatherapp.presentation.models.AppError
import com.antoine.newweatherapp.tools.ViewModelTest
import com.antoine.newweatherapp.tools.captureValues
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest : ViewModelTest() {

    private val getWeatherInfoUseCase: GetWeatherInfoUseCase = mockk()

    private val weatherMapper: WeatherMapper = mockk()

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = WeatherViewModel(getWeatherInfoUseCase, weatherMapper)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    @Test
    fun `test getWeatherForecast() with loading`() = runTest {
        // Given
        val cityName = "saigon"
        coEvery { getWeatherInfoUseCase.run(any()) } returns AppResult.Success(mockItems)

        coEvery { weatherMapper.toWeatherUI(mockItems) } returns listOf(WeatherAdapter.AdapterItem.Empty)

        val capturesLoading = viewModel.loadingObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesLoading.size, `is`(2))
        MatcherAssert.assertThat(capturesLoading[0], `is`(true))
        MatcherAssert.assertThat(capturesLoading[1], `is`(false))
        MatcherAssert.assertThat(capturesLoading[0], `is`(true))
    }

    @Test
    fun `test getWeatherForecast() with response error`() = runTest {
        // Given
        val cityName = "saigon"
        coEvery { getWeatherInfoUseCase.run(any()) } returns AppResult.GenericError

        val capturesError = viewModel.errorObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesError.size, `is`(1))
        MatcherAssert.assertThat(capturesError[0], `is`(AppError.GenericError))
    }

    @Test
    fun `test getWeatherForecast() with network error`() = runTest {
        // Given
        val cityName = "saigon"
        coEvery { getWeatherInfoUseCase.run(any()) } returns AppResult.NetworkError

        val capturesError = viewModel.errorObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesError.size, `is`(1))
        MatcherAssert.assertThat(capturesError[0], `is`(AppError.NetworkError))
    }

    @Test
    fun `test getWeatherForecast() with empty items`() = runTest {
        // Given
        val cityName = "saigon"

        coEvery { getWeatherInfoUseCase.run(any()) } returns AppResult.Success(mockItems)

        coEvery { weatherMapper.toWeatherUI(mockItems) } returns listOf(WeatherAdapter.AdapterItem.Empty)

        val capturesItems = viewModel.itemsObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesItems.size, `is`(1))
        MatcherAssert.assertThat(capturesItems[0]!![0], `is`(WeatherAdapter.AdapterItem.Empty))
    }

    @Test
    fun `test getWeatherForecast() with items`() = runTest {
        // Given
        val cityName = "saigon"
        coEvery { getWeatherInfoUseCase.run(any()) } returns AppResult.Success(mockItems)

        val adapterItems = listOf(
            WeatherAdapter.AdapterItem.WeatherUI(
                date = "Fri, 26 Nov 2021",
                tempAvg = "20",
                pressure = "40",
                humidity = "20",
                description = "description 1"
            ),
            WeatherAdapter.AdapterItem.WeatherUI(
                date = "Fri, 28 Dec 2021",
                tempAvg = "20",
                pressure = "40",
                humidity = "20",
                description = "description 1"
            ),
        )
        every { weatherMapper.toWeatherUI(mockItems) } returns adapterItems

        val capturesItems = viewModel.itemsObs.captureValues()

        // When
        viewModel.getWeatherForecast(cityName)

        // Then
        MatcherAssert.assertThat(capturesItems.size, `is`(1))
        MatcherAssert.assertThat(capturesItems[0]!![0], `is`(adapterItems[0]))
        MatcherAssert.assertThat(capturesItems[0]!![1], `is`(adapterItems[1]))
    }

    private val mockItems = listOf(
        WeatherModel(
            date = 1637899200L,
            tempAvg = 20,
            pressure = 80,
            humidity = 80,
            description = "description 1"
        ), WeatherModel(
            date = 1637402887L,
            tempAvg = 10,
            pressure = 20,
            humidity = 30,
            description = "description 2"
        ), WeatherModel(
            date = 1635847687L,
            tempAvg = 30,
            pressure = 40,
            humidity = 50,
            description = "description 3"
        )
    )
}