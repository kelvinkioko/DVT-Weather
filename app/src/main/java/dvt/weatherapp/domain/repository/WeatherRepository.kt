package dvt.weatherapp.domain.repository

import dvt.weatherapp.domain.model.WeatherModel
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double):
        Flow<Resource<WeatherModel>>

    suspend fun clearCurrentWeather()
}
