package com.example.animalshelter.model

data class Animal (
    val id: Long,
    val name: String,
    val species: String,
    val age: Int,
    val condition: AnimalCondition,
)