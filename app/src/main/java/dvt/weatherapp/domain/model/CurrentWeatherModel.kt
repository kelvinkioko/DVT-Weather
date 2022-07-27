package dvt.weatherapp.domain.model

data class CurrentWeatherModel(
    val temperature: Double,
    val minimum: Double,
    val maximum: Double,
    val weather: String,
)
