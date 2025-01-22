package com.example.animalshelter.ui.components.dialogs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.animalshelter.ui.components.generic.CustomDialog

@Composable
fun AdoptAnimalDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    CustomDialog(
        title = "Adopt Animal",
        onDismissRequest = onDismissRequest,
        onConfirm = onConfirm,
        inputFields = emptyList(),
        additionalContent = {
            Text("Are you sure you want to adopt this animal?")
        },
        isConfirmButtonEnabled = true
    )
}