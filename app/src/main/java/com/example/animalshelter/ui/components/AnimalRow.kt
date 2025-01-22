package com.example.animalshelter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalshelter.model.Animal
import com.example.animalshelter.model.AnimalCondition
import com.example.animalshelter.request.UpdateAnimalRequest
import com.example.animalshelter.ui.components.dialogs.AdoptAnimalDialog
import com.example.animalshelter.ui.components.dialogs.DeleteAnimalDialog
import com.example.animalshelter.ui.components.dialogs.EditAnimalDialog
import com.example.animalshelter.viewmodel.AnimalViewModel
import com.example.animalshelter.viewmodel.AuthViewModel

@Composable
fun AnimalRow(
    id: Long?,
    name: String,
    species: String,
    age: String,
    condition: AnimalCondition?,
    showActionButtons: Boolean = false,
    authViewModel: AuthViewModel
) {
    val viewModel: AnimalViewModel = viewModel()
    val showDeleteAnimalDialog = remember { mutableStateOf(false) }
    val showEditAnimalDialog = remember { mutableStateOf(false) }
    val showAdoptAnimalDialog = remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(name, modifier = Modifier.weight(2f))
        Text(species, Modifier.weight(2f))
        if (condition != null)
            Text(age + " " + condition.toString()[0].toString(), Modifier.weight(1.5f))
        else
            Text(age, Modifier.weight(1f))
        if (showActionButtons) {
            if (authViewModel.isAdmin()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
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
                    IconButton(
                        onClick = { showEditAnimalDialog.value = true },
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Animal",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { showAdoptAnimalDialog.value = true },
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Adopt Animal",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
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
        if (showAdoptAnimalDialog.value) {
            AdoptAnimalDialog(
                showDialog = showAdoptAnimalDialog.value,
                onDismissRequest = { showAdoptAnimalDialog.value = false },
                onConfirm = {
                    viewModel.deleteAnimal(id!!)
                    showAdoptAnimalDialog.value = false
                }
            )
        }
        if (showEditAnimalDialog.value) {
            EditAnimalDialog(
                showDialog = showEditAnimalDialog,
                onDismissRequest = { showEditAnimalDialog.value = false },
                animal = Animal(id!!, name, species, age.toInt(), condition!!),
                onConfirm = {
                    viewModel.editAnimal(
                        id,
                        UpdateAnimalRequest(
                            it.name,
                            it.species,
                            it.age,
                            it.condition
                        )
                    )
                    showEditAnimalDialog.value = false
                },
            )
        }
    }
    HorizontalDivider()
}