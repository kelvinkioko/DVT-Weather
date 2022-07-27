package dvt.weatherapp.extension

import dvt.weatherapp.util.DMY_HYPHEN
import dvt.weatherapp.util.TIMESTAMP_CALC
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.timestampFormat(pattern: String = DMY_HYPHEN): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this * TIMESTAMP_CALC))
}

fun getCurrentDate(pattern: String = DMY_HYPHEN): String {
    val calendar = Calendar.getInstance()
    return SimpleDateFormat(pattern, Locale.getDefault()).format(calendar.time)
}
