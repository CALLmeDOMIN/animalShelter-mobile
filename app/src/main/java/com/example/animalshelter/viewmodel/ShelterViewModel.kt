package com.example.animalshelter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalshelter.api.AnimalShelterService
import com.example.animalshelter.api.RetrofitClient
import com.example.animalshelter.model.AnimalShelter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ShelterViewModel : ViewModel() {
    private val animalShelterService = RetrofitClient.instance.create(AnimalShelterService::class.java)

    private val _shelters = MutableStateFlow<List<AnimalShelter>>(emptyList())
    val shelters: StateFlow<List<AnimalShelter>> get() = _shelters

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
}
