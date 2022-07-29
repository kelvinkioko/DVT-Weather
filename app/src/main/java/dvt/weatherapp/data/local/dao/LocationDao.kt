package dvt.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dvt.weatherapp.data.local.entity.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(
        locationEntity: LocationEntity
    )

    @Query("SELECT * FROM location")
    suspend fun loadLocations(): List<LocationEntity>

    @Query(
        """
            SELECT COUNT(city)
            FROM location
            WHERE latitude =:latitude AND longitude =:longitude
        """
    )
    suspend fun doesLocationExists(
        latitude: Double,
        longitude: Double
    ): Int

    @Delete
    suspend fun deleteSpecificLocations(locationEntity: LocationEntity)

    @Query("DELETE FROM location")
    suspend fun deleteAllLocations()
}
