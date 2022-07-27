package dvt.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class CurrentWeatherDTO(
    @field:Json(name = "coord") val coordinatesDto: CoordinatesDto,
    @field:Json(name = "weather") val weatherDto: List<WeatherDto> = emptyList(),
    @field:Json(name = "base") val base: String,
    @field:Json(name = "main") val currentTemperature: MainDto,
    @field:Json(name = "visibility") val visibility: Int,
    @field:Json(name = "wind") val wind: WindDto,
    @field:Json(name = "clouds") val clouds: CloudsDto,
    @field:Json(name = "dt") val currentDate: Long,
    @field:Json(name = "sys") val sysDto: SysDto,
    @field:Json(name = "timezone") val timezone: Long,
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "cod") val cod: Int
)

data class CoordinatesDto(
    @field:Json(name = "lon") val longitude: Double,
    @field:Json(name = "lat") val latitude: Double
)

data class WeatherDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "main") val main: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String
)

data class MainDto(
    @field:Json(name = "temp") val temperature: Double,
    @field:Json(name = "feels_like") val feelsLike: Double,
    @field:Json(name = "temp_min") val minimum: Double,
    @field:Json(name = "temp_max") val maximum: Double,
    @field:Json(name = "pressure") val pressure: Double,
    @field:Json(name = "humidity") val humidity: Double
)

data class WindDto(
    @field:Json(name = "speed") val speed: Double,
    @field:Json(name = "deg") val deg: Int,
    @field:Json(name = "gust") val gust: Double = 0.0
)

data class CloudsDto(
    @field:Json(name = "all") val all: Int
)

data class SysDto(
    @field:Json(name = "type") val type: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "message") val message: Float,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "sunrise") val sunrise: Long,
    @field:Json(name = "sunset") val sunset: Long
)
