package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animalshelter.model.AnimalShelter
import com.example.animalshelter.model.ShelterSummary

@Composable
fun ShelterList(
    shelters: List<ShelterSummary>,
    onShelterClick: (ShelterSummary) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        shelters.forEach { shelter ->
            ShelterCard(
                shelter = shelter,
                onClick = { onShelterClick(shelter) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}