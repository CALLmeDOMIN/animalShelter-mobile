package com.example.animalshelter.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.animalshelter.request.RatingRequest
import com.example.animalshelter.ui.components.generic.CustomDialog

@Composable
fun SubmitRatingDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: (rating: RatingRequest) -> Unit,
    shelterId: Long
) {
    val ratingValue = remember { mutableStateOf("") }
    val ratingComment = remember { mutableStateOf("") }
    val ratingError = remember { mutableStateOf<String?>(null) }

    CustomDialog(
        title = "Submit Rating",
        inputFields = listOf(
            Triple("Rating (1-5)", { ratingValue.value = it }, ratingError.value),
            Triple("Comment", { ratingComment.value = it }, null)
        ),
        onDismissRequest = { showDialog.value = false },
        onConfirm = {
            val value = ratingValue.value.toIntOrNull()
            if (value == null || value !in 1..5) {
                ratingError.value = "Please enter a valid rating between 1 and 5"
            } else {
                ratingError.value = null
                val rating = RatingRequest(
                    value = value,
                    comment = ratingComment.value,
                    shelterId
                )
                onConfirm(rating)
                showDialog.value = false
            }
        },
        isConfirmButtonEnabled = ratingValue.value.isNotEmpty() && ratingComment.value.isNotEmpty()
    )
}