package com.example.animalshelter.ui.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animalshelter.model.AnimalCondition
import com.example.animalshelter.ui.components.generic.CustomDialog
import com.example.animalshelter.ui.components.generic.CustomDropdown

@Composable
fun AddAnimalDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (name: String, species: String, age: Int, condition: AnimalCondition) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val species = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val condition = remember { mutableStateOf(AnimalCondition.HEALTHY) }

    val nameError = remember { mutableStateOf<String?>(null) }
    val speciesError = remember { mutableStateOf<String?>(null) }
    val ageError = remember { mutableStateOf<String?>(null) }

    val expanded = remember { mutableStateOf(false) }
    val isConfirmButtonEnabled = remember { mutableStateOf(false) }

    fun validateInputs() {
        isConfirmButtonEnabled.value =
            name.value.isNotBlank() && species.value.isNotBlank() && age.value.toIntOrNull() != null && condition.value != null
    }

    CustomDialog(
        title = "Add Animal",
        inputFields = listOf(
            Triple(
                "Name",
                { name.value = it; nameError.value = null; validateInputs() },
                nameError.value
            ),
            Triple(
                "Species",
                { species.value = it; speciesError.value = null; validateInputs() },
                speciesError.value
            ),
            Triple(
                "Age",
                { age.value = it; ageError.value = null; validateInputs() },
                ageError.value
            )
        ),
        additionalContent = {
            CustomDropdown(
                label = "Condition",
                value = condition,
                options = AnimalCondition.entries,
                expanded = expanded,
                onOptionSelected = {
                    condition.value = it
                    expanded.value = false
                    validateInputs()
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        onDismissRequest = onDismissRequest,
        onConfirm = {
            if (name.value.isBlank()) {
                nameError.value = "Name cannot be empty"
            }
            if (species.value.isBlank()) {
                speciesError.value = "Species cannot be empty"
            }
            if (age.value.isBlank() || age.value.toIntOrNull() == null) {
                ageError.value = "Age must be a valid number"
            }
            if (isConfirmButtonEnabled.value) {
                onConfirm(name.value, species.value, age.value.toInt(), condition.value)
                onDismissRequest()
            }
        },
        isConfirmButtonEnabled = isConfirmButtonEnabled.value
    )
}