package dvt.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dvt.weatherapp.data.local.entity.ForecastWeatherEntity

@Dao
interface ForecastWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastWeather(
        forecastWeatherEntity: List<ForecastWeatherEntity>
    )

    @Query(
        """
            SELECT *
            FROM forecast
            WHERE latitude =:latitude AND longitude =:longitude
        """
    )
    suspend fun loadWeatherForecastByCoordinates(
        latitude: Double,
        longitude: Double
    ): List<ForecastWeatherEntity>

    @Query("DELETE FROM forecast")
    suspend fun clearForecastWeather()
}
