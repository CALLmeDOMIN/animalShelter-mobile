package com.example.animalshelter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> CustomDropdown(
    label: String,
    value: MutableState<T>,
    options: List<T>,
    expanded: MutableState<Boolean>,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(label)
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, Color(0xff5d5f67))
            .padding(8.dp)
            .clickable { expanded.value = true }) {
            Text(value.value.toString(), modifier = Modifier.padding(8.dp))
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    text = { Text(option.toString()) },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value = false
                    }
                )

            }
        }
    }
}