package com.example.animalshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.animalshelter.ui.components.ShelterApp
import com.example.animalshelter.ui.theme.AnimalShelterTheme

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
