package dvt.weatherapp.domain.repository

import dvt.weatherapp.domain.model.LocationModel
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun insertLocation(
        locationModel: LocationModel
    ): Flow<Resource<Boolean>>

    suspend fun loadLocations(): List<LocationModel>

    suspend fun clearLocation()
}
