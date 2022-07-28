package dvt.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Int = 0,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
