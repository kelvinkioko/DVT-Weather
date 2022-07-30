package dvt.weatherapp.domain.model

data class WeatherModel(
    val currentWeather: CurrentWeatherModel,
    val forecastWeather: List<ForecastWeatherModel>,
    val location: LocationModel? = null
)
