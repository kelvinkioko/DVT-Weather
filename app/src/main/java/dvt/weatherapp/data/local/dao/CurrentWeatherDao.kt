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

    @Query("SELECT COUNT(currentDate) FROM current")
    suspend fun countCurrentWeather(): Int

    @Query("SELECT * FROM current WHERE currentDate =:currentDate")
    suspend fun loadCurrentWeatherByDate(
        currentDate: String
    ): CurrentWeatherEntity

    @Query("DELETE FROM current WHERE city =:city AND country =:country")
    suspend fun deleteCurrentWeatherByLocation(city: String, country: String)

    @Query("DELETE FROM current")
    suspend fun deleteCurrentWeather()
}
