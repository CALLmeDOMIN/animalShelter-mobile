package com.example.animalshelter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalshelter.api.AnimalShelterService
import com.example.animalshelter.api.RatingService
import com.example.animalshelter.api.RetrofitClient
import com.example.animalshelter.model.Animal
import com.example.animalshelter.model.AnimalShelter
import com.example.animalshelter.model.ShelterSummary
import com.example.animalshelter.request.AddAnimalShelterRequest
import com.example.animalshelter.request.RatingRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShelterViewModel : ViewModel() {
    private val animalShelterService =
        RetrofitClient.instance.create(AnimalShelterService::class.java)
    private val ratingService =
        RetrofitClient.instance.create(RatingService::class.java)

    private val _shelters = MutableStateFlow<List<ShelterSummary>>(emptyList())
    val shelters: StateFlow<List<ShelterSummary>> get() = _shelters

    private val _shelter = MutableStateFlow<AnimalShelter?>(null)
    val shelter: StateFlow<AnimalShelter?> get() = _shelter

    private val _refreshTrigger = MutableSharedFlow<Unit>()
    val refreshTrigger: SharedFlow<Unit> get() = _refreshTrigger

    fun fetchShelters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedShelters = animalShelterService.getAnimalShelters()
                _shelters.value = fetchedShelters
            } catch (e: Exception) {
                Log.e("ShelterViewModel", "Failed to fetch shelters", e)
                e.printStackTrace()
            }
        }
    }

    fun fetchShelterById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedShelter = animalShelterService.getAnimalShelterById(id)
                _shelter.value = fetchedShelter
            } catch (e: Exception) {
                Log.e("ShelterViewModel", "Failed to fetch shelter by id: $id", e)
                e.printStackTrace()
            }
        }
    }

    fun addShelter(name: String, capacity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                animalShelterService.addAnimalShelter(AddAnimalShelterRequest(name, capacity))
                _refreshTrigger.emit(Unit)
            } catch (e: Exception) {
                Log.e("ShelterViewModel", "Failed to add shelter", e)
                e.printStackTrace()
            }
        }
    }

    fun deleteShelter(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                animalShelterService.deleteAnimalShelter(id)
                delay(500)
                _refreshTrigger.emit(Unit)
            } catch (e: Exception) {
                Log.e("ShelterViewModel", "Failed to delete shelter", e)
                e.printStackTrace()
            }
        }
    }

    fun addRating(rating: RatingRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ratingService.addRating(rating)
                fetchShelterById(rating.shelterId)
            } catch (e: Exception) {
                Log.e("ShelterViewModel", "Failed to add rating", e)
                e.printStackTrace()
            }
        }
    }

    fun addAnimalToShelter(animal: Animal) {
        _shelter.value?.let { currentShelter ->
            val updatedAnimals =
                currentShelter.animals.toMutableList().apply { add(animal) }.toSet()
            _shelter.value = currentShelter.copy(animals = updatedAnimals)
        }
    }

    fun updateAnimalInShelter(animalId: Long, updatedAnimal: Animal) {
        _shelter.value?.let { currentShelter ->
            val updatedAnimals =
                currentShelter.animals.toMutableList().apply {
                    indexOfFirst { it.id == animalId }.let { index ->
                        if (index != -1) set(index, updatedAnimal)
                    }
                }.toSet()
            _shelter.value = currentShelter.copy(animals = updatedAnimals)
        }
    }

    fun removeAnimalFromShelter(animalId: Long) {
        _shelter.value?.let { currentShelter ->
            val updatedAnimals =
                currentShelter.animals.toMutableList().apply { removeIf { it.id == animalId } }
                    .toSet()
            _shelter.value = currentShelter.copy(animals = updatedAnimals)
        }
    }
}
