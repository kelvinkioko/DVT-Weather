package dvt.weatherapp.data.mapper

import dvt.weatherapp.data.local.entity.LocationEntity
import dvt.weatherapp.domain.model.LocationModel

fun LocationModel.toLocationEntity(): LocationEntity {
    return LocationEntity(
        city = city,
        country = country,
        longitude = longitude,
        latitude = latitude
    )
}
