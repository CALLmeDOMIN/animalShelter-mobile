package com.example.animalshelter.api

import com.example.animalshelter.model.AnimalShelter
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimalShelterService {
    @GET("animalshelter")
    suspend fun getAnimalShelters(): List<AnimalShelter>

    @GET("animalshelter/{id}")
    suspend fun getAnimalShelterById(@Path("id") id: Long): AnimalShelter
}