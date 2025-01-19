package com.example.animalshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.animalshelter.api.AnimalShelterService
import com.example.animalshelter.api.RetrofitClient
import com.example.animalshelter.model.AnimalShelter
import com.example.animalshelter.ui.theme.AnimalShelterTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class MainActivity : ComponentActivity() {
    private val animalShelterService = RetrofitClient.instance.create(AnimalShelterService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalShelterTheme {
                var shelters by remember { mutableStateOf<List<AnimalShelter>>(emptyList()) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        shelters = shelters,
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                LaunchedEffect(Unit) {
                    animalShelterService.getAnimalShelters().enqueue(object: Callback<List<AnimalShelter>> {
                        override fun onResponse(call: Call<List<AnimalShelter>>, response: Response<List<AnimalShelter>>) {
                            val fetchedShelters = response.body()
                            if (fetchedShelters != null) {
                                shelters = fetchedShelters
                                Log.d("MainActivity", "Fetched shelters: $fetchedShelters")
                            }
                        }

                        override fun onFailure(call: Call<List<AnimalShelter>>, t: Throwable) {
                            Log.e("MainActivity", "Failed to fetch shelter: ${t.message}")
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun Greeting(shelters: List<AnimalShelter>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        shelters.forEach { shelter ->
            Text(text = "Shelter: ${shelter.name}")
        }
    }
}