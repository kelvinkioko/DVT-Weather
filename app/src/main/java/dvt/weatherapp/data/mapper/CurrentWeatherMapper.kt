package dvt.weatherapp.data.mapper

import dvt.weatherapp.data.local.entity.CurrentWeatherEntity
import dvt.weatherapp.data.remote.dto.CurrentWeatherDTO
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.extension.format
import dvt.weatherapp.util.CLEAR
import dvt.weatherapp.util.CLEAR_ICON
import java.util.Date

fun CurrentWeatherDTO.toCurrentWeatherEntity(): CurrentWeatherEntity {
    val weatherDto = weatherDto.firstOrNull()
    return CurrentWeatherEntity(
        currentDate = Date(currentDate).format(),
        longitude = coordinatesDto.latitude,
        latitude = coordinatesDto.longitude,
        temperature = currentTemperature.temperature,
        minimum = currentTemperature.minimum,
        maximum = currentTemperature.maximum,
        pressure = currentTemperature.pressure,
        humidity = currentTemperature.humidity,
        sunrise = Date(sysDto.sunrise).format(),
        sunset = Date(sysDto.sunset).format(),
        weatherId = weatherDto?.id ?: 0,
        weather = weatherDto?.main ?: CLEAR,
        weatherIcon = weatherDto?.icon ?: CLEAR_ICON
    )
}

fun CurrentWeatherEntity.toCurrentWeatherModel(): CurrentWeatherModel {
    return CurrentWeatherModel(
        currentDate = currentDate,
        longitude = latitude,
        latitude = longitude,
        temperature = temperature,
        minimum = minimum,
        maximum = maximum,
        pressure = pressure,
        humidity = humidity,
        sunrise = sunrise,
        sunset = sunrise,
        weatherId = weatherId,
        weather = weather,
        weatherIcon = weatherIcon
    )
}
