package com.example.animalshelter.ui.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animalshelter.model.Animal
import com.example.animalshelter.model.AnimalCondition
import com.example.animalshelter.request.UpdateAnimalRequest
import com.example.animalshelter.ui.components.generic.CustomDialog
import com.example.animalshelter.ui.components.generic.CustomDropdown

@Composable
fun EditAnimalDialog(
    showDialog: MutableState<Boolean>,
    animal: Animal,
    onDismissRequest: () -> Unit,
    onConfirm: (UpdateAnimalRequest) -> Unit
) {
    var name by remember { mutableStateOf(animal.name) }
    var species by remember { mutableStateOf(animal.species) }
    val condition = remember { mutableStateOf(animal.condition) }
    var age by remember { mutableStateOf(animal.age.toString()) }
    var ageError by remember { mutableStateOf<String?>(null) }

    val expanded = remember { mutableStateOf(false) }

    if (showDialog.value) {
        CustomDialog(
            title = "Edit Animal",
            onDismissRequest = onDismissRequest,
            onConfirm = {
                val ageValue = age.toIntOrNull()
                if (ageValue == null || ageValue < 0) {
                    ageError = "Please enter a valid age"
                } else {
                    ageError = null
                    val updatedAnimal = UpdateAnimalRequest(
                        name = name,
                        species = species,
                        age = ageValue,
                        condition = condition.value
                    )
                    onConfirm(updatedAnimal)
                    showDialog.value = false
                }
            },
            inputFields = listOf(
                Triple("Name", { name = it }, null),
                Triple("Species", { species = it }, null),
                Triple("Age", { age = it }, ageError)
            ),
            initialValues = listOf(name, species, age),
            additionalContent = {
                CustomDropdown(
                    label = "Condition",
                    value = condition,
                    options = AnimalCondition.entries,
                    expanded = expanded,
                    onOptionSelected = {
                        condition.value = it
                        expanded.value = false
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            },
            isConfirmButtonEnabled = name.isNotEmpty() && species.isNotEmpty() && age.isNotEmpty()
        )
    }
}