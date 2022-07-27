package dvt.weatherapp.data.mapper

import dvt.weatherapp.data.local.entity.CurrentWeatherEntity
import dvt.weatherapp.data.remote.dto.CurrentWeatherDTO
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.extension.timestampFormat
import dvt.weatherapp.util.CLEAR
import dvt.weatherapp.util.CLEAR_ICON
import java.util.Locale

fun CurrentWeatherDTO.toCurrentWeatherEntity(): CurrentWeatherEntity {
    val weatherDto = weatherDto.firstOrNull()
    return CurrentWeatherEntity(
        currentDate = currentDate.timestampFormat(),
        longitude = coordinatesDto.latitude,
        latitude = coordinatesDto.longitude,
        temperature = currentTemperature.temperature,
        minimum = currentTemperature.minimum,
        maximum = currentTemperature.maximum,
        pressure = currentTemperature.pressure,
        humidity = currentTemperature.humidity,
        sunrise = sysDto.sunrise.timestampFormat(),
        sunset = sysDto.sunset.timestampFormat(),
        weatherId = weatherDto?.id ?: 0,
        weather = weatherDto?.main ?: CLEAR,
        weatherIcon = weatherDto?.icon ?: CLEAR_ICON
    )
}

fun CurrentWeatherEntity.toCurrentWeatherModel(): CurrentWeatherModel {
    return CurrentWeatherModel(
        temperature = String.format(Locale.getDefault(), "%.0f°", temperature),
        minimum = String.format(Locale.getDefault(), "%.0f°", minimum),
        maximum = String.format(Locale.getDefault(), "%.0f°", maximum),
        weather = weather
    )
}
