package dvt.weatherapp.extension

import dvt.weatherapp.util.DMY_HM
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format(pattern: String = DMY_HM): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}
