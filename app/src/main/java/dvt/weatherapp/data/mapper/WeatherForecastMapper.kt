package dvt.weatherapp.data.mapper

import dvt.weatherapp.data.local.entity.ForecastWeatherEntity
import dvt.weatherapp.data.remote.dto.ForecastDTO
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.extension.dateFormatter
import dvt.weatherapp.extension.getCurrentDate
import dvt.weatherapp.extension.timestampFormat
import dvt.weatherapp.util.CLEAR
import dvt.weatherapp.util.CLEAR_ICON
import dvt.weatherapp.util.WeatherIcon.Companion.getWeatherIcon
import java.util.Locale

fun ForecastDTO.toForecastWeatherEntity(): List<ForecastWeatherEntity> {
    val weatherForecasts = mutableListOf<ForecastWeatherEntity>()

    forecasts.filter { filterItem ->
        filterItem.forecastDate.timestampFormat() != getCurrentDate()
    }.map { forecast ->
        val weatherDto = forecast.weatherDto.firstOrNull()
        val forecastEntity = ForecastWeatherEntity(
            date = forecast.forecastDate.timestampFormat(),
            longitude = cityDto.coordinatesDto.latitude,
            latitude = cityDto.coordinatesDto.longitude,
            temperature = forecast.currentTemperature.temperature,
            minimum = forecast.currentTemperature.minimum,
            maximum = forecast.currentTemperature.maximum,
            pressure = forecast.currentTemperature.pressure,
            humidity = forecast.currentTemperature.humidity,
            weatherId = weatherDto?.id ?: 0,
            weather = weatherDto?.main ?: CLEAR,
            weatherIcon = weatherDto?.icon ?: CLEAR_ICON,
            city = cityDto.name,
            country = cityDto.country
        )
        weatherForecasts.add(forecastEntity)
    }

    return weatherForecasts
}

fun List<ForecastWeatherEntity>.toForecastWeatherModel(): List<ForecastWeatherModel> {
    val weatherForecasts = mutableListOf<ForecastWeatherModel>()

    this.map { forecast ->
        val forecastModel = ForecastWeatherModel(
            date = forecast.date.dateFormatter(),
            temperature = String.format(Locale.getDefault(), "%.0f°", forecast.temperature),
            weatherId = forecast.weatherId,
            weather = forecast.weather,
            weatherIcon = forecast.weatherId.getWeatherIcon()
        )

        weatherForecasts.add(forecastModel)
    }
    return weatherForecasts
}
