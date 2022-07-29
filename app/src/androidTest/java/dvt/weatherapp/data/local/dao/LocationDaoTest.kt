package dvt.weatherapp.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.local.entity.LocationEntity
import dvt.weatherapp.util.DEFAULT_LATITUDE
import dvt.weatherapp.util.DEFAULT_LONGITUDE
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class LocationDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var locationDao: LocationDao
    private val locationEntity = LocationEntity(
        city = "Nairobi",
        country = "Ke",
        latitude = DEFAULT_LATITUDE,
        longitude = DEFAULT_LONGITUDE
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        locationDao = database.locationDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertLocationItem() = runBlocking {
        locationDao.insertLocation(locationEntity = locationEntity)

        val savedLocations = locationDao.loadLocations()

        assertThat(savedLocations).contains(locationEntity)
    }

    @Test
    fun deleteSpecificLocations() = runBlocking {
        locationDao.insertLocation(locationEntity = locationEntity)
        locationDao.deleteSpecificLocations(locationEntity = locationEntity)

        val savedLocations = locationDao.loadLocations()
        assertThat(savedLocations).doesNotContain(locationEntity)
    }

    @Test
    fun deleteAllLocations() = runBlocking {
        locationDao.insertLocation(locationEntity = locationEntity)
        locationDao.deleteAllLocations()
        val savedLocations = locationDao.loadLocations()
        assertThat(savedLocations).isEmpty()
    }
}
