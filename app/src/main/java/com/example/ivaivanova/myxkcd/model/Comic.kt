package com.example.ivaivanova.myxkcd.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comics")
data class Comic(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,
    @SerializedName("month")
    val month: String,
    @SerializedName("num")
    val num: Int,
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