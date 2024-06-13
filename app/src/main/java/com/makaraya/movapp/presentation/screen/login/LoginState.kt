package com.makaraya.movapp.presentation.screen.login

data class LoginState(
    val success: String? = "",
    val error: String? = "",
    val loading: Boolean = false
)