package com.example.plantproject2024.screens

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.plantproject2024.LoginActivity
import com.example.plantproject2024.R
import com.example.plantproject2024.SearchActivity
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import com.example.plantproject2024.vm.MainViewModel
import com.google.firebase.auth.UserInfo

//Add navigation control between todo screen and plant list screen


@Composable
fun MainScreen(navController: NavController,
               context: Context,
               viewModel: MainViewModel = viewModel(),
               currentUser: UserInfo?) {
    var name by remember { mutableStateOf("") }
    var painter = painterResource(id = R.drawable.default_plant)

    val userState by viewModel.user.collectAsState()
    var UserInfo by rememberSaveable { mutableStateOf("") }

    Column {
        Row {
            if(currentUser == null){
                Text(text = "Hello, User!")
            } else {
                Text(text = "Hello, {${currentUser.email}}!")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(modifier = Modifier.size(40.dp).padding(end = 10.dp), onClick = { startLogin(context) }) {
                Image(
                    painter = painter,
                    contentDescription = "Default plant image",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }

        var selectedTab by remember { mutableStateOf(0) }

        TabRow(selectedTabIndex = selectedTab, divider = {}){
            Tab(selected = false, onClick = { selectedTab = 0} ) {
                Text(text = "To do")

            }
            Tab(selected = false, onClick = { selectedTab = 1} ) {
                Text(text = "My Plants")
            }
        }
        when (selectedTab) {
            0 -> MainTodoScreen()
            1 -> MainPlantListScreen(navController)
        }

    }
}

//Perform login activity
private fun startLogin(
    context: Context
) {
    try{
        var intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e:Exception) {
        Log.i("ActivityStarted", "start login Error, $e")
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun MainScreenPreview() {
    PlantProject2024Theme {
        MainScreen(navController = rememberNavController(), LocalContext.current.applicationContext, currentUser = null)
    }
}


