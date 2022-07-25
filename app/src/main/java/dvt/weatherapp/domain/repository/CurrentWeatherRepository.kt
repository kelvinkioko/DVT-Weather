package dvt.weatherapp.domain.repository

import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherRepository {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double):
        Flow<Resource<CurrentWeatherModel>>

    suspend fun clearCurrentWeather()
}
