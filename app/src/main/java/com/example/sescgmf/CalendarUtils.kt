package com.example.sescgmf

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarUtils
{
    var selectedDate: LocalDate? = null

    fun formattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date.format(formatter)
    }

    fun formattedTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        return time.format(formatter)
    }

    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    fun daysInMonthArray(date: LocalDate): ArrayList<LocalDate?> {
        val daysInMonthArray = ArrayList<LocalDate?>()
        val yearMonth = YearMonth.from(date)

        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = selectedDate?.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth?.dayOfWeek?.value ?: 0

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null)
            } else {
                val day = i - dayOfWeek
                daysInMonthArray.add(
                    LocalDate.of(
                        selectedDate?.year ?: LocalDate.now().year,
                        selectedDate?.monthValue ?: LocalDate.now().monthValue,
                        day
                    )
                )
            }
        }
        return daysInMonthArray
    }

    fun daysInWeekArray(selectedDate: LocalDate?): ArrayList<LocalDate> {
        val days = ArrayList<LocalDate>()
        val current = sundayForDate(selectedDate)
        val endDate = current?.plusWeeks(1)

        current?.let {
            var currentDate = it
            while (currentDate.isBefore(endDate)) {
                days.add(currentDate)
                currentDate = currentDate.plusDays(1)
            }
        }

        return days
    }

    private fun sundayForDate(current: LocalDate?): LocalDate? {
        current?.let {
            val oneWeekAgo = current.minusWeeks(1)
            var currentDate = current

            while (currentDate?.isAfter(oneWeekAgo) == true && currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
                currentDate = currentDate.minusDays(1)
            }

            if (currentDate?.dayOfWeek == DayOfWeek.SUNDAY) {
                return currentDate
            }
        }

        return null
    }


}