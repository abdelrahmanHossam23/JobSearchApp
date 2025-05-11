//package com.example.myjobsearchapplication.ui.common_components
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
////import java.lang.reflect.Modifier
//
//// CommonComponents.kt
//@Composable
//fun AuthHeader(title: String, subtitle: String) {
//    Column(
//        modifier = Modifier.padding(bottom = 32.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = title,
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = subtitle,
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//        )
//    }
//}
//
//@Composable
//fun AuthTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    modifier: Modifier = Modifier,
//    isPassword: Boolean = false,
//    keyboardType: KeyboardType = KeyboardType.Text
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        modifier = modifier.fillMaxWidth(),
//        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
//        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
//        singleLine = true
//    )
//}
//
//@Composable
//fun AuthButton(
//    text: String,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true
//) {
//    Button(
//        onClick = onClick,
//        modifier = modifier.fillMaxWidth(),
//        enabled = enabled,
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Text(text = text, modifier = Modifier.padding(vertical = 4.dp))
//    }
//}
//
//@Composable
//fun AuthFooterText(normalText: String, clickableText: String, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.Center
//    ) {
//        Text(normalText)
//        Spacer(modifier = Modifier.width(4.dp))
//        Text(
//            text = clickableText,
//            color = MaterialTheme.colorScheme.primary,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.clickable { onClick() }
//        )
//    }
//}