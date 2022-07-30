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

fun List<LocationEntity>.toLocationsModel(): List<LocationModel> {
    val locationsModel = mutableListOf<LocationModel>()

    this.map { location ->
        val locationModel = LocationModel(
            city = location.city,
            country = location.country,
            longitude = location.longitude,
            latitude = location.latitude
        )

        locationsModel.add(locationModel)
    }
    return locationsModel
}
