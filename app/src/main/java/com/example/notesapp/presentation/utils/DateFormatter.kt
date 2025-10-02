package com.example.notesapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.notesapp.R
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
    @Composable
    fun formatDateToString(timestamp: Long):String{
        val now= System.currentTimeMillis()
        val diff= now - timestamp

        return when {
            diff < millisinHours-> stringResource(R.string.just_now)
            diff < millisinDay->{
                val hours=TimeUnit.MICROSECONDS.toHours(diff)
                stringResource(R.string.h_ago, hours)
            }

            else->{
                formatter.format(timestamp)
            }
        }
    }
}