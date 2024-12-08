package com.example.plantproject2024.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SignupScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    Column {

        Text("Signup for planty!")
        OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text("Password") })
        OutlinedTextField(value = username, onValueChange = {username = it}, label = { Text("Username") })
        Button(onClick = {  }) { Text(text = "Sign Up") }
    }
}


