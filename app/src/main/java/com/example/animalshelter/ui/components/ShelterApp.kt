package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animalshelter.viewmodel.ShelterViewModel

@Composable
fun ShelterApp(navController: NavHostController) {
    val viewModel: ShelterViewModel = viewModel()
    val shelters = viewModel.shelters.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchShelters()
    }

    NavHost(navController = navController, startDestination = "shelters") {
        composable("shelters") {
            TopBar("Shelters", navController) { innerPadding ->
                ShelterList(
                    shelters = shelters.value,
                    onShelterClick = { shelter ->
                        navController.navigate("shelter/${shelter.id}")
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(
            route = "shelter/{shelterId}",
            arguments = listOf(navArgument("shelterId") { type = NavType.LongType })
        ) { backStackEntry ->
            val shelterId = backStackEntry.arguments?.getLong("shelterId")
            TopBar("Shelter Details", navController, showBackButton = true) { innerPadding ->
                SingleShelterView(shelterId = shelterId, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}