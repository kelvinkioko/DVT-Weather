package dvt.weatherapp.presentation

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import dvt.weatherapp.R
import dvt.weatherapp.databinding.ActivityMainBinding
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.extension.setNullableAdapter
import dvt.weatherapp.presentation.adapter.ForecastAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this@MainActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.sunny_color)
        window.navigationBarColor = ContextCompat.getColor(this@MainActivity, R.color.sunny_color)

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is MainUiState.CurrentWeather ->
                    renderCurrentWeather(currentWeather = state.currentWeather)
                is MainUiState.Loading -> {}
                is MainUiState.WeatherForeCast -> renderForecast(forecast = state.forecast)
            }
        }
    }

    private fun renderCurrentWeather(currentWeather: CurrentWeatherModel) {
        binding.apply {
            temperature.text = currentWeather.temperature
            weather.text = currentWeather.weather
            minTemperature.text = currentWeather.minimum
            currentTemperature.text = currentWeather.temperature
            maxTemperature.text = currentWeather.maximum
        }
    }

    private fun renderForecast(forecast: List<ForecastWeatherModel>) {
        binding.apply {
            weatherForecast.setNullableAdapter(adapter = forecastAdapter)
            forecastAdapter.submitList(forecast)
        }
    }

    private val forecastAdapter: ForecastAdapter by lazy { ForecastAdapter() }
}
