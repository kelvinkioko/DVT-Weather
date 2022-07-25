package dvt.weatherapp.data.local.dao

import androidx.room.Dao
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

    @Query(
        """
            SELECT COUNT(id)
            FROM location
            WHERE latitude =:latitude AND longitude =:longitude
        """
    )
    suspend fun doesLocationExists(
        latitude: Double,
        longitude: Double
    ): Int

    @Query("DELETE FROM location")
    suspend fun clearLocation()
}
