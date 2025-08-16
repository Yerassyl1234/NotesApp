package com.example.notesapp.presentation.utils

import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object DateFormatter {
    private val millisinHours= TimeUnit.HOURS.toMillis(1)
    private val millisinDay= TimeUnit.DAYS.toMillis(1)
    private val formatter   = SimpleDateFormat.getDateInstance(DateFormat.SHORT)

    fun formatCurrentDate():String{
        return formatter.format(System.currentTimeMillis())
    }

    fun formatDateToString(timestamp: Long):String{
        val now= System.currentTimeMillis()
        val diff= now - timestamp

        return when {
            diff < millisinHours->"Just Now"
            diff < millisinDay->{
                val hours=TimeUnit.MICROSECONDS.toHours(diff)
                "$hours h ago"
            }

            else->{
                formatter.format(timestamp)
            }
        }
    }
}