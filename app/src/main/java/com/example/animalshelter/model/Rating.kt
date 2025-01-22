package com.example.animalshelter.model

import com.google.gson.annotations.SerializedName

data class Rating(
    val id: Long,
    val value: Int,
    val comment: String,
    @SerializedName("ratingdate") val ratingDate: String
)