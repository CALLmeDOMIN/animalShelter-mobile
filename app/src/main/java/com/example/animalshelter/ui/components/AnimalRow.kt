package com.example.animalshelter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalshelter.viewmodel.AnimalViewModel

@Composable
fun AnimalRow(
    id: Long?,
    name: String,
    species: String,
    age: String,
    showDeleteButton: Boolean = false
) {
    val viewModel: AnimalViewModel = viewModel()
    val showDeleteAnimalDialog = remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, modifier = Modifier.weight(2f))
        Text(species, Modifier.weight(2f))
        Text(age, Modifier.weight(1f))
        if (showDeleteButton) {
            Box(
                Modifier
                    .weight(1f)
            ) {
                IconButton(
                    onClick = { showDeleteAnimalDialog.value = true },
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Red)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Animal",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        } else {
            Text("Actions", Modifier.weight(1f))
        }

        if (showDeleteAnimalDialog.value) {
            DeleteAnimalDialog(
                showDialog = showDeleteAnimalDialog.value,
                onDismissRequest = { showDeleteAnimalDialog.value = false },
                onConfirm = {
                    viewModel.deleteAnimal(id!!)
                    showDeleteAnimalDialog.value = false
                }
            )
        }
    }
    HorizontalDivider()
}