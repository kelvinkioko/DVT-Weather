package dvt.weatherapp.data.repository

import dvt.weatherapp.data.local.dao.LocationDao
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.mapper.toLocationEntity
import dvt.weatherapp.domain.model.LocationModel
import dvt.weatherapp.domain.repository.LocationRepository
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    weatherDatabase: WeatherDatabase
) : LocationRepository {

    private val locationDao: LocationDao = weatherDatabase.locationDao

    override suspend fun insertLocation(
        locationModel: LocationModel
    ): Flow<Resource<Boolean>> {
        return flow {
            locationDao.insertLocation(locationEntity = locationModel.toLocationEntity())

            val locationSavedState = locationDao.doesLocationExists(
                latitude = locationModel.latitude,
                longitude = locationModel.longitude
            )

            if (locationSavedState == 1)
                emit(Resource.Success(data = true))
            else
                emit(Resource.Error(message = "Couldn't save new location"))
        }
    }

    override suspend fun clearLocation() {
        locationDao.clearLocation()
    }
}
