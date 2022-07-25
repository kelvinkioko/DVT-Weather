package dvt.weatherapp.domain.model

data class ForecastWeatherModel(
    val date: String,
    val longitude: Double,
    val latitude: Double,
    val temperature: Double,
    val minimum: Double,
    val maximum: Double,
    val pressure: Double,
    val humidity: Double,
    val weatherId: Int,
    val weather: String,
    val weatherIcon: String
)
