package com.example.animalshelter.api

import com.example.animalshelter.model.AddAnimalRequest
import com.example.animalshelter.model.Animal
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface AnimalService {
    @POST("animal")
    suspend fun addAnimal(@Body request: AddAnimalRequest): Animal

    @DELETE("animal/{id}")
    suspend fun deleteAnimal(@Path("id") id: Long)
}