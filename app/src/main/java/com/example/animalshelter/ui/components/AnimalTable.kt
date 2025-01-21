package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animalshelter.model.Animal

@Composable
fun AnimalTable(animals: Set<Animal>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        AnimalRow("Name", "Species", "Age")
        animals.forEach { animal ->
            AnimalRow(
                animal.name,
                animal.species,
                animal.age.toString()
            )
        }
    }
}