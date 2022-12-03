package com.antoine.newweatherapp.data.response

import com.antoine.newweatherapp.data.response.CityResponse
import com.antoine.newweatherapp.data.response.WeatherInfoResponse
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("city")
    val city: CityResponse? = null,
    @SerializedName("list")
    val items: List<WeatherInfoResponse>? = null
)