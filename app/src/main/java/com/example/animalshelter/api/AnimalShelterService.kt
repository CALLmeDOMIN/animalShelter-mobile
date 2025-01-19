package com.example.animalshelter.api

import com.example.animalshelter.model.AnimalShelter
import retrofit2.Call
import retrofit2.http.GET

interface AnimalShelterService {
    @GET("animalshelter")
    fun getAnimalShelters(): Call<List<AnimalShelter>>
}