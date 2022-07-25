package dvt.weatherapp.data.repository

import dvt.weatherapp.data.local.dao.ForecastWeatherDao
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.mapper.toForecastWeatherEntity
import dvt.weatherapp.data.mapper.toForecastWeatherModel
import dvt.weatherapp.data.remote.WeatherApi
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.domain.repository.ForecastWeatherRepository
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastWeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    weatherDatabase: WeatherDatabase,
) : ForecastWeatherRepository {

    private val forecastWeatherDao: ForecastWeatherDao = weatherDatabase.forecastWeatherDao

    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<List<ForecastWeatherModel>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val remoteWeatherForecast = try {
                api.getWeatherForecast(latitude = latitude, longitude = longitude)
            } catch (ioException: IOException) {
                Timber.e(ioException)
                emit(Resource.Error(message = "Couldn't load weather forecast"))
                null
            } catch (httpException: HttpException) {
                Timber.e(httpException)
                emit(Resource.Error(message = "Couldn't load weather forecast"))
                null
            }

            remoteWeatherForecast?.let { forecastDTO ->
                val weatherForecasts = forecastDTO.toForecastWeatherEntity()
                forecastWeatherDao.insertForecastWeather(forecastWeatherEntity = weatherForecasts)

                val forecasts = forecastWeatherDao.loadWeatherForecastByCoordinates(
                    latitude = latitude,
                    longitude = longitude
                )

                emit(Resource.Success(data = forecasts.toForecastWeatherModel()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun clearForecastWeather() {
        forecastWeatherDao.clearForecastWeather()
    }
}
