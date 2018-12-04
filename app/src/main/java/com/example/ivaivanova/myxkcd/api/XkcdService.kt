package com.example.ivaivanova.myxkcd.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun getCurrentComic()

    /*
    Endpoint that returns a requested comics by its id/number
     */
    @GET("{comicsId}/info.0.json")
    fun getComicById(@Path("comicsId") comicsId: Long)


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