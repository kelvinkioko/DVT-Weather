package dvt.weatherapp.data.repository

import com.squareup.moshi.JsonDataException
import dvt.weatherapp.data.local.dao.CurrentWeatherDao
import dvt.weatherapp.data.local.dao.ForecastWeatherDao
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.mapper.toCurrentWeatherEntity
import dvt.weatherapp.data.mapper.toCurrentWeatherModel
import dvt.weatherapp.data.mapper.toForecastWeatherEntity
import dvt.weatherapp.data.mapper.toForecastWeatherModel
import dvt.weatherapp.data.remote.WeatherApi
import dvt.weatherapp.domain.model.WeatherModel
import dvt.weatherapp.domain.repository.WeatherRepository
import dvt.weatherapp.extension.getCurrentDate
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    weatherDatabase: WeatherDatabase
) : WeatherRepository {

    private val currentWeatherDao: CurrentWeatherDao = weatherDatabase.currentWeatherDao
    private val forecastWeatherDao: ForecastWeatherDao = weatherDatabase.forecastWeatherDao

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherModel>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val remoteCurrentWeather = try {
                coroutineScope {
                    async { api.getCurrentWeather(latitude = latitude, longitude = longitude) }
                }
            } catch (exception: JsonDataException) {
                Timber.e(exception.message ?: "Couldn't load data")
                emit(Resource.Error(message = exception.message ?: "Couldn't load data"))
                null
            }

            val remoteWeatherForecast = try {
                coroutineScope {
                    async { api.getWeatherForecast(latitude = latitude, longitude = longitude) }
                }
            } catch (exception: JsonDataException) {
                Timber.e(exception.message ?: "Couldn't load data")
                emit(Resource.Error(message = exception.message ?: "Couldn't load data"))
                null
            }

            remoteCurrentWeather?.let { currentWeather ->
                val currentWeatherEntity = currentWeather.await().toCurrentWeatherEntity()
                currentWeatherDao.insertCurrentWeather(currentWeatherEntity = currentWeatherEntity)
            }

            remoteWeatherForecast?.let { forecastDTO ->
                val weatherForecasts = forecastDTO.await().toForecastWeatherEntity()
                forecastWeatherDao.insertForecastWeather(forecastWeatherEntity = weatherForecasts)
            }

            val currentWeather = currentWeatherDao.loadCurrentWeatherByDate(
                currentDate = getCurrentDate()
            ).toCurrentWeatherModel()

            val forecastWeather = forecastWeatherDao.getWeatherForecast().toForecastWeatherModel()

            println(currentWeatherDao.loadCurrentWeatherByDate(currentDate = getCurrentDate()))
            println(forecastWeatherDao.getWeatherForecast())

            emit(
                Resource.Success(
                    data = WeatherModel(
                        currentWeather = currentWeather,
                        forecastWeather = forecastWeather
                    )
                )
            )
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun deleteCurrentWeather() {
        currentWeatherDao.deleteCurrentWeather()
    }
}
