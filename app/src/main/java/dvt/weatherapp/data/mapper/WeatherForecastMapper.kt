package dvt.weatherapp.data.mapper

import dvt.weatherapp.data.local.entity.ForecastWeatherEntity
import dvt.weatherapp.data.remote.dto.ForecastDTO
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.extension.timestampFormat
import dvt.weatherapp.util.CLEAR
import dvt.weatherapp.util.CLEAR_ICON
import java.util.Locale

fun ForecastDTO.toForecastWeatherEntity(): List<ForecastWeatherEntity> {
    val weatherForecasts = mutableListOf<ForecastWeatherEntity>()

    forecasts.map { forecast ->
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
            weatherIcon = weatherDto?.icon ?: CLEAR_ICON
        )
        weatherForecasts.add(forecastEntity)
    }

    return weatherForecasts
}

fun List<ForecastWeatherEntity>.toForecastWeatherModel(): List<ForecastWeatherModel> {
    val weatherForecasts = mutableListOf<ForecastWeatherModel>()

    this.map { forecast ->
        val forecastModel = ForecastWeatherModel(
            date = forecast.date,
            temperature = String.format(Locale.getDefault(), "%.0f°", forecast.temperature),
            weatherId = forecast.weatherId,
            weather = forecast.weather,
            weatherIcon = forecast.weatherIcon
        )

        weatherForecasts.add(forecastModel)
    }
    return weatherForecasts
}
