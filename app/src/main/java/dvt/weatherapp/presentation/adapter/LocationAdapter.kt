package dvt.weatherapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvt.weatherapp.databinding.ItemLocationBinding
import dvt.weatherapp.domain.model.LocationModel

class LocationAdapter : ListAdapter<LocationModel, LocationAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLocationBinding.inflate(
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
        private val binding: ItemLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: LocationModel) {
            binding.apply {
                locationName.text = "${forecast.city}, ${forecast.country}"
                locationCoordinates.text = "lat:${forecast.latitude},lon:${forecast.longitude}"
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<LocationModel>() {
            override fun areItemsTheSame(
                oldItem: LocationModel,
                newItem: LocationModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: LocationModel,
                newItem: LocationModel
            ): Boolean = oldItem == newItem
        }
    }
}
