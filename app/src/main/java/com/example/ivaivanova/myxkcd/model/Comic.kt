package com.example.ivaivanova.myxkcd.model

import com.google.gson.annotations.SerializedName

class Comic(
    @SerializedName("month")
    val month: String,
    @SerializedName("num")
    val num: Integer,
    @SerializedName("link")
    val link: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("news")
    val news: String,
    @SerializedName("safe_title")
    val safeTitle: String,
    @SerializedName("transcript")
    val transcript: String,
    @SerializedName("alt")
    val alt: String,
    @SerializedName("img")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("day")
    val day: String
)