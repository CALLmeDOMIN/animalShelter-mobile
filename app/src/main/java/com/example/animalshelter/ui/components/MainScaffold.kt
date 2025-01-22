package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.animalshelter.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    title: String,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    showBackButton: Boolean = false,
    dialogContent: @Composable ((MutableState<Boolean>) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val role by authViewModel.role.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = if (showBackButton) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                "Back",
                                tint = Color.White
                            )
                        }
                    }
                } else ({}),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(title, color = Color.White)
                },
                actions = {
                    if (role != null) {
                        IconButton(onClick = {
                            authViewModel.logout()
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (dialogContent != null && role == "admin") {
                FloatingActionButton(onClick = { showDialog.value = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        content = content
    )

    if (showDialog.value && dialogContent != null) {
        dialogContent(showDialog)
    }
}