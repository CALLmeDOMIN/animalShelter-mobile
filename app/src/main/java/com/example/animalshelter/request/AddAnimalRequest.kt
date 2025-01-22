package com.example.animalshelter.request

import com.example.animalshelter.model.AnimalCondition

data class AddAnimalRequest(
    val name: String,
    val species: String,
    val age: Int,
    val condition: AnimalCondition,
    val shelterId: Long
)