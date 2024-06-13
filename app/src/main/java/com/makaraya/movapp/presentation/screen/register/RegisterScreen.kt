package com.makaraya.movapp.presentation.screen.register

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.makaraya.movapp.navigation.Screen
import com.makaraya.movapp.presentation.component.NameTextField
import com.makaraya.movapp.presentation.component.PasswordTextField
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState(initial = null)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        RegisterContent(
            email = email,
            password = password,
            passwordConfirm = passwordConfirm,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onPasswordConfirmChange = { passwordConfirm = it },
            onRegisterClick = {
                coroutineScope.launch {
                    if (email.isBlank() || password.isBlank() || passwordConfirm.isBlank()) {
                        Toast.makeText(
                            context,
                            "Email, Password dan Konfirmasi Password Wajib Diisi",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (password != passwordConfirm) {
                        Toast.makeText(
                            context,
                            "Password dan Konfirmasi Password tidak cocok",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.registerUser(email, password) {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Register.route) { inclusive = true }
                            }
                            email = ""
                            password = ""
                            passwordConfirm = ""
                        }
                    }
                }
            },
            moveToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            },
            modifier = modifier
        )
        LaunchedEffect(key1 = state.value?.success) {
            coroutineScope.launch {
                if (state.value?.success?.isNotEmpty() == true) {
                    val success = state.value?.success
                    Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        LaunchedEffect(key1 = state.value?.error) {
            coroutineScope.launch {
                if (state.value?.error?.isNotEmpty() == true) {
                    val error = state.value?.error
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun RegisterContent(
    email: String,
    password: String,
    passwordConfirm: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    moveToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Daftar",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(64.dp))
            NameTextField(
                value = email,
                onValueChange = onEmailChange,
                imageVector = Icons.Outlined.Person,
                contentDescription = "Icon Person",
                label = "Email",
            )
            PasswordTextField(
                text = password,
                onValueChange = onPasswordChange,
                label = "Password"
            )
            PasswordTextField(
                text = passwordConfirm,
                onValueChange = onPasswordConfirmChange,
                label = "Konfirmasi Password"
            )
            Button(
                onClick = onRegisterClick,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Daftar",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sudah Punya Akun?")
                TextButton(onClick = moveToLogin) {
                    Text(
                        text = "Masuk",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}
