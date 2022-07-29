package dvt.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class ForecastDTO(
    @field:Json(name = "cnt") val cnt: Int,
    @field:Json(name = "list") val forecasts: List<ListDto>,
    @field:Json(name = "city") val cityDto: CityDto
)

data class ListDto(
    @field:Json(name = "dt") val forecastDate: Long,
    @field:Json(name = "main") val currentTemperature: MainDto,
    @field:Json(name = "weather") val weatherDto: List<WeatherDto>,
    @field:Json(name = "clouds") val clouds: CloudsDto,
    @field:Json(name = "wind") val wind: WindDto,
    @field:Json(name = "dt_txt") val forecastDateText: String
)

data class CityDto(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "coord") val coordinatesDto: CoordinatesDto,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "population") val population: Int,
    @field:Json(name = "timezone") val timezone: Int,
    @field:Json(name = "sunrise") val sunrise: Long,
    @field:Json(name = "sunset") val sunset: Long
)
