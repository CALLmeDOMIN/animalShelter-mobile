package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalshelter.viewmodel.ShelterViewModel

@Composable
fun SingleShelterView(
    shelterId: Long?,
    modifier: Modifier = Modifier
) {
    val viewModel: ShelterViewModel = viewModel()
    val shelter = viewModel.shelter.collectAsState()

    LaunchedEffect(Unit) {
        if (shelterId != null) {
            viewModel.fetchShelterById(shelterId)
        }
    }

    shelter.value?.let {
        Column(
            modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(3f))
                Text(
                    it.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(9f)
                )
                Text("${it.animals.size} / ${it.capacity}", Modifier.weight(2f))
            }

            AnimalTable(it.animals)
        }
    }
}