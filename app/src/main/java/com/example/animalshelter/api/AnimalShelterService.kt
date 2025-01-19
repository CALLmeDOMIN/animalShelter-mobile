package com.example.animalshelter.api

import com.example.animalshelter.model.AnimalShelter
import retrofit2.http.GET

interface AnimalShelterService {
    @GET("animalshelter")
    suspend fun getAnimalShelters(): List<AnimalShelter>
}