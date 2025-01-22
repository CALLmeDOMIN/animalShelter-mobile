package com.example.animalshelter.request

import com.example.animalshelter.model.AnimalCondition

data class UpdateAnimalRequest(
    val name: String? = null,
    val species: String? = null,
    val age: Int? = null,
    val condition: AnimalCondition? = null
)