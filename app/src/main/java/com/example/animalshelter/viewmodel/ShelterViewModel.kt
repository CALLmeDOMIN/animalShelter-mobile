package com.example.animalshelter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalshelter.api.AnimalShelterService
import com.example.animalshelter.api.RetrofitClient
import com.example.animalshelter.model.AnimalShelter
import com.example.animalshelter.model.ShelterSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShelterViewModel : ViewModel() {
    private val animalShelterService =
        RetrofitClient.instance.create(AnimalShelterService::class.java)

    private val _shelters = MutableStateFlow<List<ShelterSummary>>(emptyList())
    val shelters: StateFlow<List<ShelterSummary>> get() = _shelters

    private val _shelter = MutableStateFlow<AnimalShelter?>(null)
    val shelter: StateFlow<AnimalShelter?> get() = _shelter

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
}
