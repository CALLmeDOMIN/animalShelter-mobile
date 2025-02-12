package com.example.animalshelter.api

import com.example.animalshelter.model.AnimalShelter
import com.example.animalshelter.model.ShelterSummary
import com.example.animalshelter.request.AddAnimalShelterRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnimalShelterService {
    @GET("animalshelter")
    suspend fun getAnimalShelters(): List<ShelterSummary>

    @GET("animalshelter/{id}")
    suspend fun getAnimalShelterById(@Path("id") id: Long): AnimalShelter

    @POST("animalshelter")
    suspend fun addAnimalShelter(@Body request: AddAnimalShelterRequest): AnimalShelter

    @DELETE("animalshelter/{id}")
    suspend fun deleteAnimalShelter(@Path("id") id: Long)
}