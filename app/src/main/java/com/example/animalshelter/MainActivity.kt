package com.example.animalshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.animalshelter.ui.components.ShelterApp
import com.example.animalshelter.ui.theme.AnimalShelterTheme
import com.example.animalshelter.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimalShelterTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                ShelterApp(navController, authViewModel)
            }
        }
    }
}