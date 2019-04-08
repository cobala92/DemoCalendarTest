package com.example.phuongdangn.democalendarkotlin.utils

import android.content.Context
import com.example.phuongdangn.democalendarkotlin.R
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by phuongdn on 4/7/19.
 */
object DateUtils {

    fun getCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        setMidnight(calendar)

        return calendar
    }

    fun setMidnight(calendar: Calendar?) {
        if (calendar != null) {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }

    fun isMonthBefore(firstCalendar: Calendar?, secondCalendar: Calendar): Boolean {
        if (firstCalendar == null) {
            return false
        }

        val firstDay = firstCalendar.clone() as Calendar
        setMidnight(firstDay)
        firstDay.set(Calendar.DAY_OF_MONTH, 1)
        val secondDay = secondCalendar.clone() as Calendar
        setMidnight(secondDay)
        secondDay.set(Calendar.DAY_OF_MONTH, 1)

        return secondDay.before(firstDay)
    }

    fun isMonthAfter(firstCalendar: Calendar?, secondCalendar: Calendar): Boolean {
        if (firstCalendar == null) {
            return false
        }

        val firstDay = firstCalendar.clone() as Calendar
        setMidnight(firstDay)
        firstDay.set(Calendar.DAY_OF_MONTH, 1)
        val secondDay = secondCalendar.clone() as Calendar
        setMidnight(secondDay)
        secondDay.set(Calendar.DAY_OF_MONTH, 1)

        return secondDay.after(firstDay)
    }

    fun getMonthAndYearDate(context: Context, calendar: Calendar): String {
        return String.format(
            "%s  %s",
            context.resources.getStringArray(R.array.material_calendar_months_array)[calendar.get(Calendar.MONTH)],
            calendar.get(Calendar.YEAR)
        )
    }

    fun getMonthsBetweenDates(startCalendar: Calendar, endCalendar: Calendar): Int {
        val years = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
        return years * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)
    }

    private fun getDaysBetweenDates(startCalendar: Calendar, endCalendar: Calendar): Long {
        val milliseconds = endCalendar.timeInMillis - startCalendar.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(milliseconds)
    }

    fun isCompareDate(c1: Calendar, c2: Calendar): Boolean {
        return (c1.get(Calendar.DATE) == c2.get(Calendar.DATE)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))

    }

    fun isToday(calendar: Calendar): Boolean {
        return isCompareDate(calendar, Calendar.getInstance())
    }
}