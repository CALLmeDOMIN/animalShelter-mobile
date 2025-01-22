package com.example.animalshelter.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.animalshelter.ui.components.generic.CustomDialog

@Composable
fun AddShelterDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (name: String, capacity: Int) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val capacity = remember { mutableStateOf("") }

    val nameError = remember { mutableStateOf<String?>(null) }
    val capacityError = remember { mutableStateOf<String?>(null) }

    val isConfirmButtonEnabled = remember { mutableStateOf(false) }

    fun validateInputs() {
        isConfirmButtonEnabled.value =
            name.value.isNotBlank() && capacity.value.toIntOrNull() != null
    }

    CustomDialog(
        title = "Add Shelter",
        inputFields = listOf(
            Triple(
                "Name",
                { name.value = it; nameError.value = null; validateInputs() },
                nameError.value
            ),
            Triple(
                "Capacity",
                { capacity.value = it; capacityError.value = null; validateInputs() },
                capacityError.value
            )
        ),
        onDismissRequest = onDismissRequest,
        onConfirm = {
            if (name.value.isBlank()) {
                nameError.value = "Name cannot be empty"
            }
            if (capacity.value.isBlank() || capacity.value.toIntOrNull() == null) {
                capacityError.value = "Capacity must be a valid number"
            }
            if (isConfirmButtonEnabled.value) {
                onConfirm(name.value, capacity.value.toInt())
                onDismissRequest()
            }
        },
        isConfirmButtonEnabled = isConfirmButtonEnabled.value
    )
}