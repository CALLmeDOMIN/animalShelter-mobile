package com.example.animalshelter.model

data class AnimalShelter (
    val id: Long,
    val name: String,
    val capacity: Int,
    val animals: Set<Animal>
)