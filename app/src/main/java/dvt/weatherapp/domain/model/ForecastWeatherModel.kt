package dvt.weatherapp.domain.model

import dvt.weatherapp.util.WeatherIcon

data class ForecastWeatherModel(
    val date: String,
    val temperature: String,
    val weatherId: Int,
    val weather: String,
    val weatherIcon: WeatherIcon
)
