package com.antoine.newweatherapp.mapper

import com.antoine.newweatherapp.base.BaseTest
import com.antoine.newweatherapp.base.autoWire
import com.antoine.newweatherapp.domain.models.WeatherModel
import com.antoine.newweatherapp.presentation.fragment.adapter.WeatherAdapter
import com.antoine.newweatherapp.presentation.mapper.WeatherMapperImpl
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

class WeatherMapperImplTest : BaseTest() {

    private val weatherMapperImpl: WeatherMapperImpl = autoWire()

    @Test
    fun `verify toWeatherUI() with empty list`() {
        // Given
        val items = listOf<WeatherModel>()

        // When
        val result = weatherMapperImpl.toWeatherUI(items)

        // Then
        MatcherAssert.assertThat(result.size, `is`(1))
        MatcherAssert.assertThat(result[0], `is`(WeatherAdapter.AdapterItem.Empty))
    }

    @Test
    fun `verify toWeatherUI() with items`() {
        // Given
        val items = listOf(
            WeatherModel(
                date = 1637899200L,
                tempAvg = 20,
                pressure = 80,
                humidity = 80,
                description = "description 1"
            ),
            WeatherModel(
                date = 1637402887L,
                tempAvg = 10,
                pressure = 20,
                humidity = 30,
                description = "description 2"
            ),
            WeatherModel(
                date = 1635847687L,
                tempAvg = 30,
                pressure = 40,
                humidity = 50,
                description = "description 3"
            )
        )

        // When
        val result = weatherMapperImpl.toWeatherUI(items)

        // Then
        MatcherAssert.assertThat(result.size, `is`(3))
        (result[0] as WeatherAdapter.AdapterItem.WeatherUI).let {
            MatcherAssert.assertThat(it.date, `is`("Fri, 26 Nov 2021"))
            MatcherAssert.assertThat(it.tempAvg, `is`("20"))
            MatcherAssert.assertThat(it.pressure, `is`("80"))
            MatcherAssert.assertThat(it.humidity, `is`("80"))
            MatcherAssert.assertThat(it.description, `is`("description 1"))
        }

        (result[1] as WeatherAdapter.AdapterItem.WeatherUI).let {
            MatcherAssert.assertThat(it.date, `is`("Sat, 20 Nov 2021"))
            MatcherAssert.assertThat(it.tempAvg, `is`("10"))
            MatcherAssert.assertThat(it.pressure, `is`("20"))
            MatcherAssert.assertThat(it.humidity, `is`("30"))
            MatcherAssert.assertThat(it.description, `is`("description 2"))
        }

        (result[2] as WeatherAdapter.AdapterItem.WeatherUI).let {
            MatcherAssert.assertThat(it.date, `is`("Tue, 02 Nov 2021"))
            MatcherAssert.assertThat(it.tempAvg, `is`("30"))
            MatcherAssert.assertThat(it.pressure, `is`("40"))
            MatcherAssert.assertThat(it.humidity, `is`("50"))
            MatcherAssert.assertThat(it.description, `is`("description 3"))
        }
    }
}