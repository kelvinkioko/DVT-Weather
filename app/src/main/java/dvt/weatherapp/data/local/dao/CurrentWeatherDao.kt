package dvt.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dvt.weatherapp.data.local.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(
        currentWeatherEntity: CurrentWeatherEntity
    )

    @Query("DELETE FROM current")
    suspend fun clearCurrentWeather()
}
