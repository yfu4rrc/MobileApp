package com.example.plantproject2024

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plantproject2024.navigation.Destination
import com.example.plantproject2024.navigation.MainNavHost
import com.example.plantproject2024.screens.LoginScreen
import com.example.plantproject2024.screens.MainPlantListScreen
import com.example.plantproject2024.screens.MainScreen
import com.example.plantproject2024.screens.ProfileScreen
import com.example.plantproject2024.screens.SignupScreen
import com.example.plantproject2024.ui.component.Bottombar
import com.example.plantproject2024.ui.component.ChatBot
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import com.example.plantproject2024.vm.LoginViewModel
import com.example.plantproject2024.vm.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginActivity : ComponentActivity() {
    var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantProject2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val context: Context = applicationContext
                    //View model
                    val viewModel = viewModel<LoginViewModel>()
                    val navController = rememberNavController()
                    val authListener: FirebaseAuth.AuthStateListener = FirebaseAuth.AuthStateListener {
                        auth: FirebaseAuth ->
                        val user: FirebaseUser? = auth.currentUser
                        if (user == null) {

                        } else {

                        }
                    }
                    Log.i("ActivityStarted", "Login Activity Started")

                    if(auth.currentUser == null)
                    LoginScreen(navController, viewModel , context, modifier= Modifier.padding(innerPadding))
                    else{
                        ProfileScreen(modifier= Modifier.padding(innerPadding), context, viewModel)
                    }
                }
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        var context = applicationContext
//        var currentUser = auth.currentUser
//        if (currentUser != null) {
//            var intent = Intent(context, MainActivity::class.java)
//            finish();
//            startActivity(intent);
//        }
//
//        Log.i("ActivityStarted", "Already logged in")
//    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun LoginPreview() {
    PlantProject2024Theme {
        LoginScreen(navController = rememberNavController(), viewModel = viewModel<LoginViewModel>(), context = LocalContext.current)
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun SignupPreview() {
    PlantProject2024Theme {
        SignupScreen()
    }
}