package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animalshelter.model.Animal
import com.example.animalshelter.viewmodel.AuthViewModel

@Composable
fun AnimalTable(animals: Set<Animal>, modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    Column(modifier = modifier.padding(16.dp)) {
        AnimalRow(null, "Name", "Species", "Age", null, authViewModel = authViewModel)
        animals.forEach { animal ->
            AnimalRow(
                animal.id,
                animal.name,
                animal.species,
                animal.age.toString(),
                animal.condition,
                showActionButtons = true,
                authViewModel = authViewModel
            )
        }
    }
}