package com.example.animalshelter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimalViewModelFactory(private val shelterViewModel: ShelterViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            return AnimalViewModel(shelterViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}