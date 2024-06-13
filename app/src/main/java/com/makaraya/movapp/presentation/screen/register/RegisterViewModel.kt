package com.makaraya.movapp.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makaraya.movapp.data.firebase.AuthRepository
import com.makaraya.movapp.data.firebase.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = Channel<RegisterState>()
    val state = _state.receiveAsFlow()

    fun registerUser(email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            repository.registerUser(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.send(RegisterState(success = "Register Berhasil"))
                        home()
                    }

                    is Resource.Loading -> {
                        _state.send(RegisterState(loading = true))
                    }

                    is Resource.Error -> {
                        _state.send(RegisterState(error = result.message))
                    }
                }
            }
        }
    }
}