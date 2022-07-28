package dvt.weatherapp.domain.model

data class CurrentWeatherModel(
    val temperature: String,
    val minimum: String,
    val maximum: String,
    val weatherId: Int,
    val weather: String
)
