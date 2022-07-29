package dvt.weatherapp.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dvt.weatherapp.data.local.database.WeatherDatabase
import dvt.weatherapp.data.local.entity.ForecastWeatherEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ForecastWeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var forecastWeatherDao: ForecastWeatherDao

    private var thikaForecast = arrayListOf(
        ForecastWeatherEntity(date = "30-07-2022", longitude = -1.1018, latitude = 37.0113, temperature = 17.63, minimum = 17.63, maximum = 17.63, pressure = 1017.0, humidity = 71.0, weatherId = 500, weather = "Rain", weatherIcon = "10n", city = "Thika", country = "KE"),
        ForecastWeatherEntity(date = "31-07-2022", longitude = -1.1018, latitude = 37.0113, temperature = 17.61, minimum = 17.61, maximum = 17.61, pressure = 1017.0, humidity = 69.0, weatherId = 804, weather = "Clouds", weatherIcon = "04n", city = "Thika", country = "KE"),
        ForecastWeatherEntity(date = "01-08-2022", longitude = -1.1018, latitude = 37.0113, temperature = 17.05, minimum = 17.05, maximum = 17.05, pressure = 1018.0, humidity = 72.0, weatherId = 500, weather = "Rain", weatherIcon = "10n", city = "Thika", country = "KE"),
        ForecastWeatherEntity(date = "02-08-2022", longitude = -1.1018, latitude = 37.0113, temperature = 19.12, minimum = 19.12, maximum = 19.12, pressure = 1016.0, humidity = 48.0, weatherId = 803, weather = "Clouds", weatherIcon = "04n", city = "Thika", country = "KE"),
        ForecastWeatherEntity(date = "03-08-2022", longitude = -1.1018, latitude = 37.0113, temperature = 23.83, minimum = 23.83, maximum = 23.83, pressure = 1016.0, humidity = 38.0, weatherId = 801, weather = "Clouds", weatherIcon = "02d", city = "Thika", country = "KE"),
    )
    private var nairobiForecast = arrayListOf(
        ForecastWeatherEntity(date = "30-07-2022", longitude = -1.2864, latitude = 36.8172, temperature = 16.85, minimum = 16.85, maximum = 16.85, pressure = 1017.0, humidity = 77.0, weatherId = 500, weather = "Rain", weatherIcon = "10n", city = "Nairobi", country = "KE"),
        ForecastWeatherEntity(date = "31-07-2022", longitude = -1.2864, latitude = 36.8172, temperature = 16.86, minimum = 16.86, maximum = 16.86, pressure = 1017.0, humidity = 71.0, weatherId = 804, weather = "Clouds", weatherIcon = "04n", city = "Nairobi", country = "KE"),
        ForecastWeatherEntity(date = "01-08-2022", longitude = -1.2864, latitude = 36.8172, temperature = 16.8, minimum = 16.8, maximum = 16.8, pressure = 1018.0, humidity = 70.0, weatherId = 804, weather = "Clouds", weatherIcon = "04n", city = "Nairobi", country = "KE"),
        ForecastWeatherEntity(date = "02-08-2022", longitude = -1.2864, latitude = 36.8172, temperature = 18.57, minimum = 18.57, maximum = 18.57, pressure = 1016.0, humidity = 51.0, weatherId = 803, weather = "Clouds", weatherIcon = "04n", city = "Nairobi", country = "KE"),
        ForecastWeatherEntity(date = "03-08-2022", longitude = -1.2864, latitude = 36.8172, temperature = 22.36, minimum = 22.36, maximum = 22.36, pressure = 1016.0, humidity = 41.0, weatherId = 800, weather = "Clear", weatherIcon = "01d", city = "Nairobi", country = "KE")
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        forecastWeatherDao = database.forecastWeatherDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertForecastWeather() = runBlocking {
        forecastWeatherDao.insertForecastWeather(forecastWeatherEntity = thikaForecast)

        val savedForecast = forecastWeatherDao.getWeatherForecast()

        assertThat(thikaForecast).isEqualTo(savedForecast)
    }

    @Test
    fun getWeatherForecast() = runBlocking {
        forecastWeatherDao.insertForecastWeather(forecastWeatherEntity = thikaForecast)

        val savedForecast = forecastWeatherDao.getWeatherForecast()

        assertThat(thikaForecast).isEqualTo(savedForecast)
    }

    @Test
    fun loadWeatherForecastByLocation() = runBlocking {
        forecastWeatherDao.insertForecastWeather(
            forecastWeatherEntity = thikaForecast + nairobiForecast
        )

        val savedForecast = forecastWeatherDao.loadWeatherForecastByLocation(
            city = "Thika",
            country = "KE"
        )

        assertThat(savedForecast).isEqualTo(thikaForecast)
    }

    @Test
    fun deleteCurrentWeatherByLocation() = runBlocking {
        forecastWeatherDao.insertForecastWeather(
            forecastWeatherEntity = thikaForecast + nairobiForecast
        )
        forecastWeatherDao.deleteCurrentWeatherByLocation(city = "Thika", country = "KE")

        val savedForecastCount = forecastWeatherDao.getWeatherForecast().size

        assertThat(savedForecastCount).isEqualTo(5)
    }

    @Test
    fun deleteForecastWeather() = runBlocking {
        forecastWeatherDao.insertForecastWeather(
            forecastWeatherEntity = thikaForecast + nairobiForecast
        )
        forecastWeatherDao.deleteForecastWeather()

        val savedForecastCount = forecastWeatherDao.getWeatherForecast().size

        assertThat(savedForecastCount).isEqualTo(0)
    }
}
