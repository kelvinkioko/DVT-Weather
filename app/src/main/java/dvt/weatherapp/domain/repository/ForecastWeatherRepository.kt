package dvt.weatherapp.domain.repository

import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ForecastWeatherRepository {

    suspend fun getWeatherForecast(latitude: Double, longitude: Double):
        Flow<Resource<List<ForecastWeatherModel>>>

    suspend fun clearForecastWeather()
}
