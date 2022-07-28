package dvt.weatherapp.domain.model

data class ForecastWeatherModel(
    val date: String,
    val temperature: String,
    val weatherId: Int,
    val weather: String,
    val weatherIcon: String
)
