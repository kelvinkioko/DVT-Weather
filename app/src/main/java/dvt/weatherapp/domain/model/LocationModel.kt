package dvt.weatherapp.domain.model

data class LocationModel(
    val id: Int,
    val city: String,
    val country: String,
    val longitude: Double,
    val latitude: Double
)
