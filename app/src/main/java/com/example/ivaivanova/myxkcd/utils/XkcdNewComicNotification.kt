package com.example.ivaivanova.myxkcd.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.lang.UnsupportedOperationException
import java.util.*

// Using a Service or WorkManager for scheduling a task for a background work
class XkcdNewComicNotification : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        /*while (day) {
            Calendar.MONDAY
            Calendar.WEDNESDAY
            Calendar.FRIDAY
            else ->
        }*/

        return super.onStartCommand(intent, flags, startId)
    }
}