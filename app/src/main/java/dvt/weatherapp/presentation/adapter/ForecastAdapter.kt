package dvt.weatherapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvt.weatherapp.databinding.ItemForecastBinding
import dvt.weatherapp.domain.model.ForecastWeatherModel

class ForecastAdapter : ListAdapter<ForecastWeatherModel, ForecastAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecast = currentList[position])
    }

    inner class ViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: ForecastWeatherModel) {
            binding.apply {
                forecastDay.text = forecast.date
                forecastTemperature.text = forecast.temperature
                val context = forecastWeatherIcon.context
                forecastWeatherIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        forecast.weatherIcon.iconRes,
                        context.theme
                    )
                )
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ForecastWeatherModel>() {
            override fun areItemsTheSame(
                oldItem: ForecastWeatherModel,
                newItem: ForecastWeatherModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ForecastWeatherModel,
                newItem: ForecastWeatherModel
            ): Boolean = oldItem == newItem
        }
    }
}
