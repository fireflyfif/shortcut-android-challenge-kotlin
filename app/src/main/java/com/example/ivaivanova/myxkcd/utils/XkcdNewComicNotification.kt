package com.example.ivaivanova.myxkcd.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.ivaivanova.myxkcd.App
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.ui.MainActivity
import retrofit2.Call
import retrofit2.Response
import java.util.*

// Using a Service or WorkManager for scheduling a task for a background work
class XkcdNewComicNotification : Service() {

    private val tag = XkcdNewComicNotification::class.java.simpleName
    var comicId: Int? = 0

    private val api = XkcdService.create()
    // Get an instance of the Preferences class
    val preferences: ComicsPreferences = ComicsPreferences(App.context)

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        val date = Calendar.getInstance().set(1, 3)

        when (day) {
            Calendar.MONDAY -> getRecentComic()
            Calendar.WEDNESDAY -> getRecentComic()
            Calendar.FRIDAY -> getRecentComic()
            Calendar.HOUR -> getRecentComic()
            else -> stopSelf() // Stop the service, if it was previously started.
        }

        return super.onStartCommand(intent, flags, startId)
    }

    // Get the recent comic from the xkcd API
    private fun getRecentComic() {
        api.getCurrentComic().enqueue(object : retrofit2.Callback<Comic> {

            // TODO: Q: Do I need a list here?
            val comicList = mutableListOf<Comic>()

            override fun onResponse(call: Call<Comic>?, response: Response<Comic>) {
                if (response.isSuccessful) {
                    val currentComic: Comic? = response.body()
                    comicList.add(currentComic!!)

                    // Get the recent comic number
                    comicId = currentComic.num
                    Log.d(tag, "Current comic ID is $comicId")

                    val latestComicNum: Int? = preferences.getComicNumber()
                    Log.d(tag, "Last saved comic num in preferences is $latestComicNum")

                    // TODO: Only display notification if there is a new comic
                    // Compare the current comic number with the one saved in SharedPreferences
                    if (comicId!! > latestComicNum!!) {
                        // Display the Notification here
                        displayNotification("New comic published", comicId.toString())
                    }

                } else {

                }
            }

            override fun onFailure(call: Call<Comic>?, t: Throwable?) {
                Log.e(tag, "Failed to fetch data.")

            }
        })
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

        val pattern = longArrayOf(0, 300, 0)

        // Create the notification
        val notification = NotificationCompat.Builder(applicationContext, "newcomicid")
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(resultIntent)
            .setContentTitle(title)
            .setContentText(task)
            .setVibrate(pattern)
            .setSmallIcon(R.drawable.ic_number_sign)
            .setAutoCancel(true)

        notificationManager.notify(1, notification.build())
    }
}