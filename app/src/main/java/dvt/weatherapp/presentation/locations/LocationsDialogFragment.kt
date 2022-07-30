package dvt.weatherapp.presentation.locations

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dvt.weatherapp.R
import dvt.weatherapp.databinding.LocationsDialogFragmentBinding
import dvt.weatherapp.domain.model.LocationModel
import dvt.weatherapp.extension.setNullableAdapter
import dvt.weatherapp.extension.viewBinding
import dvt.weatherapp.presentation.adapter.LocationAdapter
import dvt.weatherapp.util.SHEET_HEIGHT

class LocationsDialogFragment(
    private val locationsModel: List<LocationModel>
) : BottomSheetDialogFragment() {

    private val binding by viewBinding(LocationsDialogFragmentBinding::bind)

    /**
     * This function makes BottomSheetDialogFragment full screen and without collapsed state
     * For some reason this doesn't work without the params.height
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val params = bottomSheet.layoutParams
            val height: Int = Resources.getSystem().displayMetrics.heightPixels
            val maxHeight = (height * SHEET_HEIGHT).toInt()
            params.height = maxHeight
            bottomSheet.layoutParams = params

            val behaviour = BottomSheetBehavior.from(bottomSheet)

            behaviour.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
                isDraggable = false
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.locations_dialog_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()
        setupLocations()
    }

    private fun setupToolBar() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
    }

    private fun setupLocations() {
        binding.apply {
            locations.setNullableAdapter(adapter = locationAdapter)
            locationAdapter.submitList(locationsModel)
        }
    }

    private val locationAdapter: LocationAdapter by lazy { LocationAdapter() }
}
