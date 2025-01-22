package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.animalshelter.request.RatingRequest

@Composable
fun SubmitRating(
    onSubmit: (RatingRequest) -> Unit,
    shelterId: Long
) {
    val value = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }

    Column {
        TextField(
            value = value.value,
            onValueChange = { value.value = it },
            label = { Text("Rating (1-5)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = comment.value,
            onValueChange = { comment.value = it },
            label = { Text("Comment") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val rating = RatingRequest(
                    value = value.value.toInt(),
                    comment = comment.value,
                    shelterId
                )
                onSubmit(rating)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}