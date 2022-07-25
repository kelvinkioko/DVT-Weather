package dvt.weatherapp.domain.model

data class CurrentWeatherModel(
    val currentDate: String,
    val longitude: Double,
    val latitude: Double,
    val temperature: Double,
    val minimum: Double,
    val maximum: Double,
    val pressure: Double,
    val humidity: Double,
    val sunrise: String,
    val sunset: String,
    val weatherId: Int,
    val weather: String,
    val weatherIcon: String
)
