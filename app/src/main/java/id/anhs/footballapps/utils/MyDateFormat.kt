package id.anhs.footballapps.utils

import java.text.SimpleDateFormat
import java.util.*

object MyDateFormat {

    fun dateEn(strDates: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(strDates)
        val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())

        return outputFormat.format(date)
    }

    fun time(strTimes: String): String {
        val times = strTimes.split(":","+")
        val strHour = times[0]
        val strMinute = times[1]

        val hour = strHour.toInt()
        val minute = strMinute.toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val date = calendar.time

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return dateFormat.format(date)
    }

    fun gregorianDateTimeInMillis(strDates: String, strTimes: String): Long {
        val dates = strDates.split("-")
        val strYear = dates[0]
        val strMonth = dates[1]
        val strDay = dates[2]

        val times = strTimes.split(":","+")
        val strHour = times[0]
        val strMinute = times[1]

        val year = strYear.toInt()
        val month = strMonth.toInt()
        val day = strDay.toInt()
        val hour = strHour.toInt()
        val minute = strMinute.toInt()

        val calendar = GregorianCalendar(year, month-1, day, hour, minute)

        return calendar.timeInMillis
    }

}