package dvt.weatherapp.data.local.entity

import androidx.room.Entity

@Entity(tableName = "current", primaryKeys = ["currentDate", "city", "country"])
data class CurrentWeatherEntity(
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
    val weatherIcon: String,
    val city: String,
    val country: String
)
