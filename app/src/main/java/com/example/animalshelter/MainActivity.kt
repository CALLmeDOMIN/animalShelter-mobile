package com.example.animalshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animalshelter.ui.components.ShelterList
import com.example.animalshelter.ui.components.SingleShelterView
import com.example.animalshelter.ui.theme.AnimalShelterTheme
import com.example.animalshelter.viewmodel.ShelterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimalShelterTheme {
                val navController = rememberNavController()
                ShelterApp(navController)
            }
        }
    }
}

@Composable
fun ShelterApp(navController: NavHostController) {
    val viewModel: ShelterViewModel = viewModel()
    val shelters = viewModel.shelters.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchShelters()
    }

    NavHost(navController = navController, startDestination = "shelters") {
        composable("shelters") {
            ShelterList(
                shelters = shelters.value,
                onShelterClick = { shelter ->
                    navController.navigate("shelter/${shelter.id}")
                }
            )
        }
        composable(
            route = "shelter/{shelterId}",
            arguments = listOf(navArgument("shelterId") { type = NavType.LongType })
        ) { backStackEntry ->
            val shelterId = backStackEntry.arguments?.getLong("shelterId")
            SingleShelterView(shelterId = shelterId)
        }
    }
}