package dvt.weatherapp.data.remote

import dvt.weatherapp.data.remote.dto.CurrentWeatherDTO
import dvt.weatherapp.data.remote.dto.ForecastDTO
import dvt.weatherapp.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): CurrentWeatherDTO

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY
    ): ForecastDTO
}
