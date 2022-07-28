package dvt.weatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dvt.weatherapp.domain.location.LocationTracker
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.domain.repository.WeatherRepository
import dvt.weatherapp.util.DEFAULT_LATITUDE
import dvt.weatherapp.util.DEFAULT_LONGITUDE
import dvt.weatherapp.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _uiState = MutableLiveData<MainUiState>()
    val uiState: LiveData<MainUiState> = _uiState

    private var latitude: Double = DEFAULT_LATITUDE
    private var longitude: Double = DEFAULT_LONGITUDE

    fun loadWeather() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                latitude = location.latitude
                longitude = location.longitude
            }
            weatherRepository.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).onEach { resource ->
                when (resource) {
                    is Resource.Error -> println("Resource message ${resource.message}")
                    is Resource.Loading ->
                        _uiState.value = MainUiState.Loading(isLoading = resource.isLoading)
                    is Resource.Success -> {
                        resource.data?.currentWeather?.let {
                            _uiState.value = MainUiState.CurrentWeather(currentWeather = it)
                        }

                        _uiState.value = MainUiState.WeatherForeCast(
                            forecast = resource.data?.forecastWeather ?: emptyList()
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
