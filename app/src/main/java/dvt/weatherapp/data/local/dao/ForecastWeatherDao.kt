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

    @Query("SELECT * FROM forecast")
    suspend fun getWeatherForecast(): List<ForecastWeatherEntity>

    @Query("SELECT * FROM forecast WHERE city =:city AND country =:country")
    suspend fun loadWeatherForecastByLocation(city: String, country: String):
        List<ForecastWeatherEntity>

    @Query("DELETE FROM forecast WHERE city =:city AND country =:country")
    suspend fun deleteCurrentWeatherByLocation(city: String, country: String)

    @Query("DELETE FROM forecast")
    suspend fun deleteForecastWeather()
}
