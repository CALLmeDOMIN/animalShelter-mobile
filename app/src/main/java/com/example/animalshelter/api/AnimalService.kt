package com.example.animalshelter.api

import com.example.animalshelter.model.AddAnimalRequest
import com.example.animalshelter.model.Animal
import retrofit2.http.Body
import retrofit2.http.POST

interface AnimalService {
    @POST("animal")
    suspend fun addAnimal(@Body request: AddAnimalRequest): Animal
}