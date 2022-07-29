package dvt.weatherapp.data.local.entity

import androidx.room.Entity

@Entity(tableName = "forecast", primaryKeys = ["date", "city", "country"])
data class ForecastWeatherEntity(
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
    val weatherIcon: String,
    val city: String,
    val country: String
)
