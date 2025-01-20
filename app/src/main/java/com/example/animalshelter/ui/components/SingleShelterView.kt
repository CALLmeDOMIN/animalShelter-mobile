package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalshelter.viewmodel.ShelterViewModel

@Composable
fun SingleShelterView(shelterId: Long?) {
    val viewModel: ShelterViewModel = viewModel()
    val shelter = viewModel.shelter.collectAsState()

    LaunchedEffect(Unit) {
        if (shelterId != null) {
            viewModel.fetchShelterById(shelterId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            shelter.value?.let {
                Text(text = "Name: ${it.name}")
                Text(text = "Capacity: ${it.capacity}")
                Text(text = "Animals: ${it.animals.size}")
                it.animals.forEach { animal ->
                    Text(text = animal.name)
                }
            }
        }
    }
}