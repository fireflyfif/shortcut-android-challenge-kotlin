package com.example.ivaivanova.myxkcd.api

import android.util.Log
import com.example.ivaivanova.myxkcd.model.Comic
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * XKCD API communication setup via Retrofit
 */
interface XkcdService {

    /*
    Endpoint that returns always the latest comics
     */
    @GET("/info.0.json")
    fun getCurrentComic(): Call<Comic>

    /*
    Endpoint that returns a requested comics by its id/number
     */
    // TODO: Not sure if this comicId should be Int?
    @GET("{comicsId}/info.0.json")
    fun getComicById(@Path("comicsId") comicsId: Int): Call<Comic>


    companion object {
        private const val BASE_URL = "https://xkcd.com/"

        /*
        Method that creates the Retrofit object
         */
        fun create(): XkcdService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(XkcdService::class.java) // TODO: This gets the Java Class object, source: https://stackoverflow.com/a/45341542
        }
    }
}

fun makeQuery(
    service: XkcdService,
    comicId: Int,
    onSuccess: (comics: List<Comic>) -> Unit,
    onError: (error: String) -> Unit
) {
    service.getComicById(comicId).enqueue(object : Callback<Comic> {

        val comicList = mutableListOf<Comic>()

        override fun onResponse(call: Call<Comic>, response: Response<Comic>) {
            if (response.isSuccessful) {
                val currentComic = response.body()

                if (currentComic != null) {
                    //comicId--

                    onSuccess(listOf(currentComic))
                    // COMPLETED: Return once the comic number reaches 1
                    if (comicId == 1) {
                        return
                    }

                    comicList.add(currentComic)
                    Log.d("XkcdDataSource", "LoadAfter: Current comic ID is $comicId")

                }
            } else {
                onError(response.errorBody()?.string() ?: "Unknown error")
            }
        }

        override fun onFailure(call: Call<Comic>, t: Throwable) {

        }
    })
}