package com.example.plantproject2024

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.plantproject2024.screens.MainPlantListScreen
import com.example.plantproject2024.screens.MainScreen
import com.example.plantproject2024.ui.component.Bottombar
import com.example.plantproject2024.ui.component.ChatBot
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import com.example.plantproject2024.vm.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

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
                    val viewModel = viewModel<MainViewModel>()
                    val navController = rememberNavController()

                    //firestore db
                    val fs_db = Firebase.firestore
                    var currentUser = Firebase.auth.currentUser
                    var currentUserId = currentUser?.uid
                    var userRef = fs_db.collection("users")

                    userRef.whereEqualTo("uid", currentUserId).get().addOnSuccessListener {
                        documents ->
                        for(document in documents){
                            Log.i("MainActivity", "Document found: ${document.id}${document.data}")
                        }


                    }

                    //get collection


                    //Set user
                    App(navController, modifier= Modifier.padding(innerPadding), viewModel, context, currentUser)
                }
            }
        }
    }

    @SuppressLint("UnsafeIntentLaunch")
    override fun onStart() {
        super.onStart()
//        var currentUser = auth.currentUser
//        if (currentUser != null) {
//            val intent = intent
//            finish();
//            startActivity(intent);
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App (navController: NavHostController, modifier: Modifier, viewModel: MainViewModel, context: Context, currentUser: UserInfo?) {
    Scaffold (
        //Bottom bar
        bottomBar = {Bottombar(navController)},
        floatingActionButton = {ChatBot(viewModel)},
    ) { innerPadding ->

        //Nav host for screens
        MainNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding),
            context = context,
            currentUser = currentUser
        )

    }
}


@Preview(showBackground = true, apiLevel = 34)
@Composable
fun AppPreview() {
    PlantProject2024Theme {
        App(navController = rememberNavController(), modifier= Modifier, viewModel = viewModel<MainViewModel>(), context = LocalContext.current, currentUser = null)
    }
}
@Preview(showBackground = true, apiLevel = 34)
@Composable
fun PlantListPreview() {
    PlantProject2024Theme {
        MainPlantListScreen(navController = rememberNavController())
    }
}