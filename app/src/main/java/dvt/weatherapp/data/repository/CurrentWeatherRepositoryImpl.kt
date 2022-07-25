package dvt.weatherapp.data.repository

import dvt.weatherapp.data.local.dao.CurrentWeatherDao
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.mapper.toCurrentWeatherEntity
import dvt.weatherapp.data.mapper.toCurrentWeatherModel
import dvt.weatherapp.data.remote.WeatherApi
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.domain.repository.CurrentWeatherRepository
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    weatherDatabase: WeatherDatabase
) : CurrentWeatherRepository {

    private val dao: CurrentWeatherDao = weatherDatabase.currentWeatherDao

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<CurrentWeatherModel>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val remoteCurrentWeather = try {
                api.getCurrentWeather(latitude = latitude, longitude = longitude)
            } catch (ioException: IOException) {
                Timber.e(ioException)
                emit(Resource.Error(message = "Couldn't load data"))
                null
            } catch (httpException: HttpException) {
                Timber.e(httpException)
                emit(Resource.Error(message = "Couldn't load data"))
                null
            }

            remoteCurrentWeather?.let { currentWeather ->
                val currentWeatherEntity = currentWeather.toCurrentWeatherEntity()
                dao.insertCurrentWeather(
                    currentWeatherEntity = currentWeatherEntity
                )

                val currentWeatherModel = dao.loadCurrentWeatherByDate(
                    currentDate = currentWeatherEntity.currentDate
                ).toCurrentWeatherModel()

                emit(Resource.Success(data = currentWeatherModel))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun clearCurrentWeather() {
        dao.clearCurrentWeather()
    }
}
