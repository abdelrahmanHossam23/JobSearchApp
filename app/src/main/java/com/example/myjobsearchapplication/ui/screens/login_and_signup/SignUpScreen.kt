package com.example.myjobsearchapplication.ui.screens.login_and_signup


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myjobsearchapplication.ui.screens.login_and_signup.viemodel.SignUpEvent
import com.example.myjobsearchapplication.ui.screens.login_and_signup.viemodel.SignUpViewModel
import com.example.myjobsearchapplication.ui.theme.Purple40


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    val email = "viewModel.email.collectAsState()"
    val password = "viewModel.password.collectAsState()"
    val confirmPassword = "viewModel.confirmPassword.collectAsState()"

    val state by viewModel.state.collectAsState()

    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            onSignUpSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple40),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = state.email,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.EmailChanged(it))
                            },
            placeholder = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple40),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = state.password,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.PasswordChanged(it))
                            },
            placeholder = { Text("Password") },
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
            visualTransformation = PasswordVisualTransformation()
        )

//        OutlinedTextField(
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp, 4.dp)
//                .border(
//                    BorderStroke(width = 2.dp, color = Purple40),
//                    shape = RoundedCornerShape(50)
//                ),
//            colors = TextFieldDefaults.textFieldColors(
//                containerColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            ),
//            value = confirmPassword,
//            onValueChange = {
////                viewModel.updateConfirmPassword(it)
//                            },
//            placeholder = { Text("confirm password") },
//            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email") },
//            visualTransformation = PasswordVisualTransformation()
//        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        Button(
            onClick = {
                viewModel.onEvent(SignUpEvent.Submit)
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Sign Up")
            }

//            Text(
//                text = "Sign Up",
//                fontSize = 16.sp,
//                modifier = Modifier.padding(0.dp, 6.dp)
//            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AuthPreview() {
//    JobSearchApplicationTheme {
//        SignUpScreen({ _, _ -> })
//    }
//}