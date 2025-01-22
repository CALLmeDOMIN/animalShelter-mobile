package com.example.animalshelter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalshelter.api.AnimalService
import com.example.animalshelter.api.RetrofitClient
import com.example.animalshelter.model.AnimalCondition
import com.example.animalshelter.request.AddAnimalRequest
import com.example.animalshelter.request.UpdateAnimalRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalViewModel(private val shelterViewModel: ShelterViewModel) : ViewModel() {
    private val animalService = RetrofitClient.instance.create(AnimalService::class.java)

    fun addAnimal(
        name: String,
        species: String,
        age: Int,
        condition: AnimalCondition,
        shelterId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newAnimal = animalService.addAnimal(
                    AddAnimalRequest(name, species, age, condition, shelterId)
                )
                shelterViewModel.addAnimalToShelter(newAnimal)
            } catch (e: Exception) {
                Log.e("AnimalViewModel", "Failed to create animal", e)
                e.printStackTrace()
            }
        }
    }

    fun editAnimal(animalId: Long, newAnimal: UpdateAnimalRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedAnimal = animalService.updateAnimal(animalId, newAnimal)
                shelterViewModel.updateAnimalInShelter(animalId, updatedAnimal)
            } catch (e: Exception) {
                Log.e("AnimalViewModel", "Failed to update animal", e)
                e.printStackTrace()
            }
        }
    }

    fun deleteAnimal(animalId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                animalService.deleteAnimal(animalId)
                shelterViewModel.removeAnimalFromShelter(animalId)
            } catch (e: Exception) {
                Log.e("AnimalViewModel", "Failed to delete animal", e)
                e.printStackTrace()
            }
        }
    }
}