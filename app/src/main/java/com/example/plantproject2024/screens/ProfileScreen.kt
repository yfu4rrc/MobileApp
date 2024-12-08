package com.example.plantproject2024.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.example.plantproject2024.MainActivity
import com.example.plantproject2024.R
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import com.example.plantproject2024.vm.LoginViewModel
import com.example.plantproject2024.vm.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, context: Context = LocalContext.current.applicationContext, viewModel: LoginViewModel) {

    Column {
        Text("Profile")
        Image(painter = painterResource(id = R.drawable.default_plant), contentDescription = "Profile")
        Button(onClick = {viewModel.getUserInfo()}) {
            Text("Get User Info")
        }
        Button(onClick = {logout(context)}){
            Text("Logout")
        }
    }
}
private fun logout(context: Context){
    Firebase.auth.signOut()
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun ProfileScreenPreview() {
    PlantProject2024Theme {
        ProfileScreen(modifier = Modifier, viewModel = LoginViewModel())
    }
}