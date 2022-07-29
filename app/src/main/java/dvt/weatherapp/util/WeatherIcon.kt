package dvt.weatherapp.util

import androidx.annotation.DrawableRes
import dvt.weatherapp.R

sealed class WeatherIcon(
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherIcon(
        iconRes = R.drawable.ic_sunny
    )
    object Cloudy : WeatherIcon(
        iconRes = R.drawable.ic_cloudy
    )
    object Rainy : WeatherIcon(
        iconRes = R.drawable.ic_rainshower
    )

    companion object {
        fun Int.getWeatherIcon(): WeatherIcon {
            return when (this) {
                in THUNDER_MIN..THUNDER_MAX,
                in DRIZZLE_MIN..DRIZZLE_MAX,
                in RAIN_MIN..RAIN_MAX,
                in SNOW_MIN..SNOW_MAX -> Rainy
                in CLOUD_MIN..CLOUD_MAX -> Cloudy
                else -> ClearSky
            }
        }
    }
}
