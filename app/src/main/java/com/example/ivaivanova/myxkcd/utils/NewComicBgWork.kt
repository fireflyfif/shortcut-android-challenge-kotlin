package com.example.ivaivanova.myxkcd.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ivaivanova.myxkcd.R


class NewComicBgWork(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val comicNumber: String? = inputData.getString(TASK_DESC)

        // TODO: Do some work here - schedule fetching new comics every Monday, Wed, and Friday

        return Result.success()
    }

    private fun displayNotification(title: String, task: String) {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    "simplifiedcoding", "simplifiedcoding",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(channel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val notification = NotificationCompat.Builder(applicationContext,
            "simplifiedcoding")
            .setContentTitle(title)
            .setContentText(task)
            .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notification.build())
    }

    companion object {
        const val TASK_DESC = "task_desc"
    }


}