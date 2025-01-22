package com.example.animalshelter.request

data class RatingRequest(
    val value: Int,
    val comment: String,
    val shelterId: Long
)