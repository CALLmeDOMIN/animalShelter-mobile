package com.example.animalshelter.api

import com.example.animalshelter.model.Animal
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalService {
    @POST("animals")
    fun createAnimal(@Body animal: Animal): Call<Animal>

    @GET("animals")
    fun getAllAnimals(): Call<List<Animal>>
}