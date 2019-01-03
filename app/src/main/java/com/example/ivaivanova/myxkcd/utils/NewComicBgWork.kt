package com.example.ivaivanova.myxkcd.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.ui.MainActivity

// WorkManager tutorial helper: http://thetechnocafe.com/how-to-use-workmanager-in-android/
class NewComicBgWork(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val comicNumber: String? = inputData.getString(TASK_DESC)

        // TODO: Do some work here - schedule fetching new comics every Monday, Wed, and Friday
        displayNotification("New comic published", comicNumber)

        val outputData: Data = Data.Builder()
            .putString(NewComicBgWork.TASK_DESC, "This received result here.")
            .build()

        return Result.success(outputData)
    }


    private fun displayNotification(title: String, task: String?) {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // We need to specify the channel for devices that run SDK 26 and above
        // Notification Channel Id is ignored for Android pre O (26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    "newcomicid", "newcomicid",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(channel)
        }

        // Make explicit Intent to open the MainActivity with all comics
        // TODO: Make it open the detail Activity with the most recent comic
        val comicsIntent = Intent(applicationContext, MainActivity::class.java)

        // Make pending intent to open an activity when a notification is tapped
        val resultIntent: PendingIntent = PendingIntent
            .getActivity(applicationContext, 0, comicsIntent, 0)


        // Create the notification
        val notification = NotificationCompat.Builder(applicationContext, "newcomicid")
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(resultIntent)
            .setContentTitle(title)
            .setContentText(task)
            .setSmallIcon(R.drawable.ic_number_sign)
            .setAutoCancel(true)

        notificationManager.notify(1, notification.build())
    }

    companion object {
        const val TASK_DESC = "task_desc"
    }


}