package com.example.animalshelter.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _role = MutableStateFlow<String?>(null)
    val role: StateFlow<String?> get() = _role

    fun login(username: String, password: String): Boolean {
        return when {
            username == "user" && password == "user" -> {
                _role.value = "user"
                true
            }

            username == "admin" && password == "admin" -> {
                _role.value = "admin"
                true
            }

            else -> {
                false
            }
        }
    }

    fun isAdmin(): Boolean {
        return _role.value == "admin"
    }

    fun logout() {
        _role.value = null
    }
}