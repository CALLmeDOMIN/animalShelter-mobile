package com.example.animalshelter.model

data class AddAnimalRequest(
    val name: String,
    val species: String,
    val age: Int,
    val condition: AnimalCondition,
    val shelterId: Long
)