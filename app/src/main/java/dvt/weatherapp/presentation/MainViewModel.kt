package dvt.weatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import dvt.weatherapp.domain.location.LocationTracker
import dvt.weatherapp.domain.model.CurrentWeatherModel
import dvt.weatherapp.domain.model.ForecastWeatherModel
import dvt.weatherapp.domain.model.LocationModel
import dvt.weatherapp.domain.repository.LocationRepository
import dvt.weatherapp.domain.repository.WeatherRepository
import dvt.weatherapp.presentation.locations.LocationsDialogFragment
import dvt.weatherapp.util.DEFAULT_LATITUDE
import dvt.weatherapp.util.DEFAULT_LONGITUDE
import dvt.weatherapp.util.Resource
import dvt.weatherapp.util.SingleLiveEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _uiState = MutableLiveData<MainUiState>()
    val uiState: LiveData<MainUiState> = _uiState

    private val _action = SingleLiveEvent<MainActions>()
    val action: LiveData<MainActions> = _action

    private var latitude: Double = DEFAULT_LATITUDE
    private var longitude: Double = DEFAULT_LONGITUDE
    private var locationModel: LocationModel? = null

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
                        locationModel = resource.data?.location

                        _uiState.value = MainUiState.LocationModel(
                            showSaveButton = locationModel != null
                        )

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

    fun saveLocationPrompt() {
        locationModel?.let {
            _uiState.value = MainUiState.SaveLocationPrompt(
                locationDetails = "${it.city}, ${it.country}"
            )
        }
    }

    fun saveLocation() {
        viewModelScope.launch {
            locationModel?.let {
                locationRepository.insertLocation(
                    locationModel = it
                ).onEach { resource ->
                    when (resource) {
                        is Resource.Error -> println("Resource message ${resource.message}")
                        is Resource.Loading -> println("Resource message ${resource.message}")
                        is Resource.Success -> _uiState.value = MainUiState.SaveLocationPrompt(
                            saveStatus = resource.data
                        )
                    }
                }.launchIn(this)
            }
        }
    }

    fun showLocations() {
        viewModelScope.launch {
            val locationsModel = locationRepository.loadLocations()

            val bottomSheet = LocationsDialogFragment(
                locationsModel = locationsModel
            )
            _action.value = MainActions.ShowLocations(bottomSheet = bottomSheet)
        }
    }
}

sealed class MainActions {
    data class ShowLocations(val bottomSheet: BottomSheetDialogFragment) : MainActions()
}

sealed class MainUiState {
    data class Loading(
        val isLoading: Boolean = true
    ) : MainUiState()

    data class LocationModel(
        val showSaveButton: Boolean = true
    ) : MainUiState()

    data class CurrentWeather(
        val currentWeather: CurrentWeatherModel
    ) : MainUiState()

    data class WeatherForeCast(
        val forecast: List<ForecastWeatherModel>
    ) : MainUiState()

    data class SaveLocationPrompt(
        val saveStatus: Boolean? = null,
        val locationDetails: String? = null
    ) : MainUiState()
}
