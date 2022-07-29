package dvt.weatherapp.data.local.entity

import androidx.room.Entity

@Entity(tableName = "location", primaryKeys = ["city", "country", "latitude", "longitude"])
data class LocationEntity(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
