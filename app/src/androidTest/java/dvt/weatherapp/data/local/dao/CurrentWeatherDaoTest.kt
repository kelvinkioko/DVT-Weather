package dvt.weatherapp.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.local.entity.CurrentWeatherEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrentWeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var currentWeatherDao: CurrentWeatherDao
    private val currentWeather = CurrentWeatherEntity(
        currentDate = "29-07-2022",
        longitude = -1.1065,
        latitude = 37.0155,
        temperature = 20.1,
        minimum = 18.96,
        maximum = 20.66,
        pressure = 1015.0,
        humidity = 54.0,
        sunrise = "29-07-2022",
        sunset = "29-07-2022",
        weatherId = 804,
        weather = "Clouds",
        weatherIcon = "04d",
        city = "Thika",
        country = "KE"
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        currentWeatherDao = database.currentWeatherDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCurrentWeather() = runBlocking {
        currentWeatherDao.insertCurrentWeather(currentWeatherEntity = currentWeather)

        val savedCurrentWeather = currentWeatherDao.loadCurrentWeatherByDate(
            currentDate = currentWeather.currentDate
        )

        assertThat(savedCurrentWeather).isEqualTo(currentWeather)
    }

    @Test
    fun insertDuplicateCurrentWeather() = runBlocking {
        currentWeatherDao.insertCurrentWeather(currentWeatherEntity = currentWeather)
        currentWeatherDao.insertCurrentWeather(currentWeatherEntity = currentWeather)

        val weatherCount = currentWeatherDao.countCurrentWeather()

        assertThat(weatherCount).isEqualTo(1)
    }

    @Test
    fun deleteCurrentWeatherByLocation() = runBlocking {
        currentWeatherDao.insertCurrentWeather(currentWeatherEntity = currentWeather)
        currentWeatherDao.deleteCurrentWeatherByLocation(city = currentWeather.city, country = currentWeather.country)

        val weatherCount = currentWeatherDao.countCurrentWeather()

        assertThat(weatherCount).isEqualTo(0)
    }

    @Test
    fun deleteCurrentWeather() = runBlocking {
        currentWeatherDao.insertCurrentWeather(currentWeatherEntity = currentWeather)
        currentWeatherDao.deleteCurrentWeather()

        val weatherCount = currentWeatherDao.countCurrentWeather()

        assertThat(weatherCount).isEqualTo(0)
    }
}
