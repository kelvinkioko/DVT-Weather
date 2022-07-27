package dvt.weatherapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dvt.weatherapp.databinding.ActivityMainBinding
import dvt.weatherapp.domain.model.CurrentWeatherModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is MainUiState.CurrentWeather ->
                    renderCurrentWeather(currentWeather = state.currentWeather)
                is MainUiState.Loading -> {}
                is MainUiState.WeatherForeCast -> {}
            }
        }
    }

    private fun renderCurrentWeather(currentWeather: CurrentWeatherModel) {
        binding.apply {
            temperature.text = "${currentWeather.temperature}°"
            weather.text = currentWeather.weather
            minTemperature.text = "${currentWeather.minimum}°"
            currentTemperature.text = "${currentWeather.temperature}°"
            maxTemperature.text = "${currentWeather.maximum}°"
        }
    }
}
