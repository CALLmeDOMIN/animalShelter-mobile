package com.example.animalshelter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    title: String,
    inputFields: List<Triple<String, (String) -> Unit, String?>>,
    additionalContent: @Composable (() -> Unit)? = null,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    isConfirmButtonEnabled: Boolean
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(title, modifier = Modifier.padding(bottom = 8.dp))
            inputFields.forEach { (label, onValueChange, errorMessage) ->
                val textState = remember { mutableStateOf("") }
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    OutlinedTextField(
                        value = textState.value,
                        onValueChange = {
                            textState.value = it
                            onValueChange(it)
                        },
                        label = { Text(label) },
                        isError = errorMessage != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorMessage != null) {
                        Text(
                            errorMessage,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            additionalContent?.let {
                it()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = onConfirm,
                    enabled = isConfirmButtonEnabled,
                    colors = ButtonDefaults.buttonColors(containerColor = if (isConfirmButtonEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}