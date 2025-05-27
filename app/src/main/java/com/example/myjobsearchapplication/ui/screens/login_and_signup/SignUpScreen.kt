package com.example.myjobsearchapplication.ui.screens.login_and_signup


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.R
import com.example.myjobsearchapplication.ui.screens.login_and_signup.viemodel.SignUpEvent
import com.example.myjobsearchapplication.ui.screens.login_and_signup.viemodel.SignUpViewModel
import com.example.myjobsearchapplication.ui.theme.DarkCeruleanBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            onSignUpSuccess()
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = DarkCeruleanBlue,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                value = state.name,
                onValueChange = {
                viewModel.onEvent(SignUpEvent.NameChanged(it))
                },
                label = { Text("Full Name") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.id_card),
                        contentDescription = "Email",
                        tint = if (state.nameError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                },
            isError = state.nameError != null,
            supportingText = {
                state.nameError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = DarkCeruleanBlue,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                value = state.email,
                onValueChange = {
                viewModel.onEvent(SignUpEvent.EmailChanged(it))
                },
                label = { Text("Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = if (state.emailError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            },
            isError = state.emailError != null,
            supportingText = {
                state.emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = DarkCeruleanBlue,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                value = state.password,
                onValueChange = {
                viewModel.onEvent(SignUpEvent.PasswordChanged(it))
                },
                label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = if (state.passwordError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle Password Visibility")
                    }
                },
            isError = state.passwordError != null,
            supportingText = {
                state.passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
                visualTransformation =  if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = DarkCeruleanBlue,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                value = state.confirmPassword,
                onValueChange = {
                viewModel.onEvent(SignUpEvent.ConfirmPasswordChanged(it))
                },
                label = { Text("Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = if (state.confirmPasswordError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle Confirm Password Visibility")
                    }
                },
                isError = state.confirmPasswordError != null,
            supportingText = {
                state.confirmPasswordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp)
            )

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

            Button(
                onClick = {
                viewModel.onEvent(SignUpEvent.Submit)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            enabled = !state.isLoading,
                shape = RoundedCornerShape(12.dp)
            ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            } else {
                Text("Sign Up", modifier = Modifier.padding(vertical = 8.dp))
            }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text("Already have an account? ")
                Text(
                    text = "Sign In",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        onNavigateToSignIn()
                    }
                )

            }

        }
    }


}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AuthPreview() {
//    SignUpScreen(
//        onNavigateToSignIn = {},
//    onSignUpSuccess = {}
//    )
//
//}