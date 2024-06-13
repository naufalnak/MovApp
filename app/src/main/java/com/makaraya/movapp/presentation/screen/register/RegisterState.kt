package com.makaraya.movapp.presentation.screen.register

data class RegisterState(
    val success: String? = null,
    val loading: Boolean = false,
    val error: String? = null
)