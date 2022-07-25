package dvt.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Int = 0,
    val longitude: Double,
    val latitude: Double
)
