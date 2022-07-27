package dvt.weatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dvt.weatherapp.domain.model.ForecastWeatherModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableLiveData<MainUiState>()
    val uiState: LiveData<MainUiState> = _uiState
}

sealed class MainUiState {
    data class Loading(
        val isLoading: Boolean = true
    ) : MainUiState()

    data class CurrentWeather(
        val temperature: Double,
        val minimum: Double,
        val maximum: Double,
        val weather: String
    ) : MainUiState()

    data class WeatherForeCast(
        val forecast: List<ForecastWeatherModel>
    ) : MainUiState()
}
