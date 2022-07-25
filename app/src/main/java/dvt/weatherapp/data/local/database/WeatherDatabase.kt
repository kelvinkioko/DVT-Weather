package dvt.weatherapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dvt.weatherapp.data.local.dao.CurrentWeatherDao
import dvt.weatherapp.data.local.dao.ForecastWeatherDao
import dvt.weatherapp.data.local.dao.LocationDao
import dvt.weatherapp.data.local.entity.CurrentWeatherEntity
import dvt.weatherapp.data.local.entity.ForecastWeatherEntity
import dvt.weatherapp.data.local.entity.LocationEntity

@Database(
    entities = [CurrentWeatherEntity::class, ForecastWeatherEntity::class, LocationEntity::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val currentWeatherDao: CurrentWeatherDao
    abstract val forecastWeatherDao: ForecastWeatherDao
    abstract val locationDao: LocationDao
}
