package com.example.animalshelter.ui.components.dialogs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.animalshelter.ui.components.generic.CustomDialog

@Composable
fun DeleteShelterDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    CustomDialog(
        title = "Delete Shelter",
        onDismissRequest = onDismissRequest,
        onConfirm = onConfirm,
        inputFields = emptyList(),
        additionalContent = {
            Text("Are you sure you want to delete this shelter?")
        },
        isConfirmButtonEnabled = true
    )
}