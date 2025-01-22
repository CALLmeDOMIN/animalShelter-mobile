package com.example.animalshelter.api

import com.example.animalshelter.model.Rating
import com.example.animalshelter.request.RatingRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RatingService {
    @POST("rating")
    suspend fun addRating(@Body request: RatingRequest): Rating
}