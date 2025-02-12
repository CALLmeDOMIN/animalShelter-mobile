package com.example.animalshelter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.animalshelter.request.RatingRequest
import com.example.animalshelter.ui.components.dialogs.DeleteShelterDialog
import com.example.animalshelter.ui.components.dialogs.SubmitRatingDialog
import com.example.animalshelter.viewmodel.AuthViewModel
import com.example.animalshelter.viewmodel.ShelterViewModel

@Composable
fun SingleShelterView(
    shelterId: Long?,
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val viewModel: ShelterViewModel = viewModel()
    val shelter = viewModel.shelter.collectAsState()

    val deletionConfirmed = remember { mutableStateOf(false) }
    val showDeleteShelterDialog = remember { mutableStateOf(false) }
    val showSubmitRatingDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (shelterId != null) {
            viewModel.fetchShelterById(shelterId)
        }
    }

    shelter.value?.let {
        Column(
            modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(1f))
                if (authViewModel.isAdmin()) {
                    IconButton(
                        onClick = { showDeleteShelterDialog.value = true },
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Red)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Shelter",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Text(
                    it.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(9f)
                )
                Text("${it.animals.size} / ${it.capacity}", Modifier.weight(2f))
            }

            AnimalTable(it.animals, authViewModel = authViewModel)
            RatingList(it.ratings)
            Button(
                onClick = { showSubmitRatingDialog.value = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit Rating")
            }

            if (showSubmitRatingDialog.value) {
                SubmitRatingDialog(
                    showDialog = showSubmitRatingDialog,
                    onConfirm = {
                        viewModel.addRating(
                            RatingRequest(
                                value = it.value,
                                comment = it.comment,
                                shelterId = shelterId!!
                            )
                        )
                        showSubmitRatingDialog.value = false
                    },
                    shelterId = shelterId!!
                )
            }

            if (showDeleteShelterDialog.value) {
                DeleteShelterDialog(
                    showDialog = showDeleteShelterDialog.value,
                    onDismissRequest = { showDeleteShelterDialog.value = false },
                    onConfirm = {
                        viewModel.deleteShelter(it.id)
                        showDeleteShelterDialog.value = false
                        deletionConfirmed.value = true
                    }
                )
            }
        }
    }

    LaunchedEffect(deletionConfirmed.value) {
        viewModel.refreshTrigger.collect {
            navController.navigate("shelters") {
                popUpTo("shelters") { inclusive = true }
            }
        }
    }
}