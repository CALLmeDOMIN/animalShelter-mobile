package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animalshelter.ui.components.dialogs.AddAnimalDialog
import com.example.animalshelter.ui.components.dialogs.AddShelterDialog
import com.example.animalshelter.viewmodel.AnimalViewModel
import com.example.animalshelter.viewmodel.AnimalViewModelFactory
import com.example.animalshelter.viewmodel.AuthViewModel
import com.example.animalshelter.viewmodel.ShelterViewModel

@Composable
fun ShelterApp(navController: NavHostController, authViewModel: AuthViewModel) {
    val role by authViewModel.role.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (role == null) "login" else "shelters"
    ) {
        composable("login") {
            LoginScreen(authViewModel, onLoginSuccess = { navController.navigate("shelters") })
        }
        composable("shelters") { HomeScreen(navController, authViewModel) }
        composable(
            route = "shelter/{shelterId}",
            arguments = listOf(navArgument("shelterId") { type = NavType.LongType })
        ) { backStackEntry -> ShelterDetails(navController, backStackEntry, authViewModel) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val viewModel: ShelterViewModel = viewModel()
    val shelters by viewModel.shelters.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchShelters()
    }

    LaunchedEffect(viewModel.refreshTrigger) {
        viewModel.refreshTrigger.collect {
            viewModel.fetchShelters()
        }
    }

    MainScaffold(
        "Shelters",
        navController,
        authViewModel,
        dialogContent = { showDialog ->
            if (showDialog.value) {
                AddShelterDialog(
                    onDismissRequest = { showDialog.value = false },
                    onConfirm = { name, capacity ->
                        viewModel.addShelter(name, capacity)
                        showDialog.value = false
                    }
                )
            }
        }
    ) { innerPadding ->
        ShelterList(
            shelters = shelters,
            onShelterClick = { shelter ->
                navController.navigate("shelter/${shelter.id}")
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ShelterDetails(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    authViewModel: AuthViewModel
) {
    val shelterViewModel: ShelterViewModel = viewModel()
    val animalViewModel: AnimalViewModel =
        viewModel(factory = AnimalViewModelFactory(shelterViewModel))

    val shelterId = backStackEntry.arguments?.getLong("shelterId")
    MainScaffold(
        "Shelter Details",
        navController,
        authViewModel,
        showBackButton = true,
        dialogContent = { showDialog ->
            if (showDialog.value) {
                AddAnimalDialog(
                    onDismissRequest = { showDialog.value = false },
                    onConfirm = { name, species, age, condition ->
                        animalViewModel.addAnimal(name, species, age, condition, shelterId!!)
                        showDialog.value = false
                    }
                )
            }
        }
    ) { innerPadding ->
        SingleShelterView(
            shelterId = shelterId,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            authViewModel = authViewModel
        )
    }
}