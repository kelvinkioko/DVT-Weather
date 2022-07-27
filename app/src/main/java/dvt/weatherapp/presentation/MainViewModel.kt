package dvt.weatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.domain.repository.WeatherRepository
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<MainUiState>()
    val uiState: LiveData<MainUiState> = _uiState

    init {
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            weatherRepository.getCurrentWeather(
                latitude = -1.286389,
                longitude = 36.817223
            ).onEach { resource ->
                when (resource) {
                    is Resource.Error -> println("Resource message ${resource.message}")
                    is Resource.Loading -> println("Resource loading ${resource.isLoading}")
                    is Resource.Success -> {
                        resource.data?.currentWeather?.let {
                            _uiState.value = MainUiState.CurrentWeather(currentWeather = it)
                        }

                        val forecast = resource.data?.forecastWeather ?: emptyList()
                        _uiState.value = MainUiState.WeatherForeCast(
                            forecast = forecast
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}

sealed class MainUiState {
    data class Loading(
        val isLoading: Boolean = true
    ) : MainUiState()

    data class CurrentWeather(
        val currentWeather: CurrentWeatherModel
    ) : MainUiState()

    data class WeatherForeCast(
        val forecast: List<ForecastWeatherModel>
    ) : MainUiState()
}
