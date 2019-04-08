package com.example.phuongdangn.democalendarkotlin

import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Month
import java.time.MonthDay
import java.util.*

object TimeUtil {
    private const val FORMAT_YEAR_MONTH = "yyyy/MM"
    private const val DT_FORMAT_YEAR_MONTH_DAY = "dd-MM-yyyy"
    private const val DT_FORMAT_YEAR_MONTH_DATE_SERVER = "yyyy-MM-dd"
    private const val DT_FORMAT_YEAR_MONTH = "yyyy-MM"

    fun getTimeFromCal(calendar: Calendar, dtFormat: String): String {
        return DateFormat.format(dtFormat, calendar).toString()
    }

    fun getDateMedicineTable(addMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, addMonth)
        return DateFormat.format(DT_FORMAT_YEAR_MONTH_DATE_SERVER, calendar).toString()
    }

    fun getDaysYearMonth(calendar: Calendar): Int {
        return calendar.getActualMaximum(Calendar.DATE)
    }

    fun getDayPositionOffset(calendar: Calendar): Int {
        calendar.set(Calendar.DATE, 1)
        return (calendar.get(Calendar.DAY_OF_WEEK) + 6) % Calendar.DAY_OF_WEEK
    }

    fun isCompareDate(c1: Calendar, c2: Calendar): Boolean {
        return (c1.get(Calendar.DATE) == c2.get(Calendar.DATE)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))

    }

    fun isToday(calendar: Calendar): Boolean {
        return isCompareDate(calendar, Calendar.getInstance())
    }

    fun getFormattedYearMonth(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat(FORMAT_YEAR_MONTH, Locale.JAPAN)
        return dateFormat.format(calendar.time)
    }

    fun getYearMonth(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat(DT_FORMAT_YEAR_MONTH, Locale.JAPAN)
        return dateFormat.format(calendar.time)
    }

    fun getDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat(DT_FORMAT_YEAR_MONTH_DATE_SERVER, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getCalendar(date: String): Calendar {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val calendar = Calendar.getInstance()
            calendar.time = format.parse(date)
            return calendar
        } catch (e: ParseException) {
            throw RuntimeException("Illegal date: $date", e)
        }

    }

    fun getNumberMonthBetWeenTwoDays(c1: Calendar, c2: Calendar): Int {
        val diffYear = Math.abs(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))
        return diffYear * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)
    }

    fun convertTime(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return getTimeFromCal(calendar, DT_FORMAT_YEAR_MONTH_DAY)
    }

    fun convertDate(date: String): Date {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        try {
            return format.parse(date)
        } catch (e: ParseException) {
            throw RuntimeException("Illegal date: $date", e)
        }

    }


    fun parseDate(timeAtMiliseconds: Long): String {
        if (timeAtMiliseconds <= 0) {
            return ""
        }
        val result = "Vừa xong."
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dataSot = formatter.format(Date())
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = timeAtMiliseconds

        val agoformater = formatter.format(calendar.time)

        var CurrentDate: Date? = null
        var CreateDate: Date? = null

        try {
            CurrentDate = formatter.parse(dataSot)
            CreateDate = formatter.parse(agoformater)

            var different = Math.abs(CurrentDate!!.time - CreateDate!!.time)

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            val elapsedDays = different / daysInMilli
            different = different % daysInMilli

            val elapsedHours = different / hoursInMilli
            different = different % hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different = different % minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            different = different % secondsInMilli
            if (elapsedDays == 0L) {
                if (elapsedHours == 0L) {
                    if (elapsedMinutes == 0L) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s"
                        } else {
                            if (elapsedDays > 0 && elapsedSeconds < 59) {
                                return "Vừa xong."
                            }
                        }
                    } else {
                        return elapsedMinutes.toString() + " phút trước."
                    }
                } else {
                    return elapsedHours.toString() + " giờ trước"
                }

            } else {
                if (elapsedDays <= 29) {
                    return elapsedDays.toString() + " ngày trước"
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1 tháng trước"
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2 tháng trước"
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3 tháng trước"
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4 tháng trước"
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5 tháng trước"
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6 tháng trước"
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7 tháng trước"
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8 tháng trước"
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9 tháng trước"
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10 tháng trước"
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11 tháng trước"
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12 tháng trước"
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 năm trước"
                }

                if (elapsedDays > 720) {
                    val formatterYear = SimpleDateFormat("dd/MM/yyyy")
                    val calendarYear = Calendar.getInstance()
                    calendarYear.timeInMillis = timeAtMiliseconds
                    return formatterYear.format(calendarYear.time) + ""
                }

            }

        } catch (e: java.text.ParseException) {
            e.printStackTrace()
        }

        return result
    }
}