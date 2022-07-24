package dvt.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastWeatherEntity(
    @PrimaryKey val currentDate: String,
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
