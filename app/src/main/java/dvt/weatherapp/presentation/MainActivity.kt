package dvt.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import dagger.hilt.android.AndroidEntryPoint
import dvt.weatherapp.R
import dvt.weatherapp.databinding.ActivityMainBinding
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.extension.setNullableAdapter
import dvt.weatherapp.presentation.adapter.ForecastAdapter
import dvt.weatherapp.util.CLOUD_MAX
import dvt.weatherapp.util.CLOUD_MIN
import dvt.weatherapp.util.DRIZZLE_MAX
import dvt.weatherapp.util.DRIZZLE_MIN
import dvt.weatherapp.util.LOTTIE_PADDING
import dvt.weatherapp.util.RAIN_MAX
import dvt.weatherapp.util.RAIN_MIN
import dvt.weatherapp.util.SNOW_MAX
import dvt.weatherapp.util.SNOW_MIN
import dvt.weatherapp.util.THUNDER_MAX
import dvt.weatherapp.util.THUNDER_MIN

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loader.setPadding(LOTTIE_PADDING)
        setUpObservers()

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeather()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is MainUiState.CurrentWeather ->
                    renderCurrentWeather(currentWeather = state.currentWeather)
                is MainUiState.Loading ->
                    renderLoadingState(isLoading = state.isLoading)
                is MainUiState.WeatherForeCast -> renderForecast(forecast = state.forecast)
            }
        }
    }

    private fun renderLoadingState(isLoading: Boolean) {
        binding.apply {
            loader.isVisible = isLoading
            weatherGroup.isGone = isLoading
        }
    }

    private fun renderCurrentWeather(currentWeather: CurrentWeatherModel) {
        binding.apply {
            temperature.text = currentWeather.temperature
            weather.text = currentWeather.weather
            minTemperature.text = currentWeather.minimum
            currentTemperature.text = currentWeather.temperature
            maxTemperature.text = currentWeather.maximum

            renderStyleBasedOnWeather(weatherId = currentWeather.weatherId)
        }
    }

    private fun renderForecast(forecast: List<ForecastWeatherModel>) {
        binding.apply {
            weatherForecast.setNullableAdapter(adapter = forecastAdapter)
            forecastAdapter.submitList(forecast)
        }
    }

    private val forecastAdapter: ForecastAdapter by lazy { ForecastAdapter() }

    private fun renderStyleBasedOnWeather(weatherId: Int) {
        binding.apply {
            val window: Window = this@MainActivity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            when (weatherId) {
                in THUNDER_MIN..THUNDER_MAX,
                in DRIZZLE_MIN..DRIZZLE_MAX,
                in RAIN_MIN..RAIN_MAX,
                in SNOW_MIN..SNOW_MAX -> { // Rainy
                    parent.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.rainy_color))
                    window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.rainy_status_color)
                    window.navigationBarColor = ContextCompat.getColor(this@MainActivity, R.color.rainy_nav_color)
                }
                in CLOUD_MIN..CLOUD_MAX -> { // Cloudy
                    parent.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.cloudy_color))
                    window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.cloudy_status_color)
                    window.navigationBarColor = ContextCompat.getColor(this@MainActivity, R.color.cloudy_nav_color)
                }
                else -> { // Clear
                    parent.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.sunny_color))
                    window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.sunny_status_color)
                    window.navigationBarColor = ContextCompat.getColor(this@MainActivity, R.color.sunny_nav_color)
                }
            }
        }
    }
}
