package com.example.plantproject2024.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantproject2024.R
import com.example.plantproject2024.SearchActivity
import com.example.plantproject2024.ui.states.SearchUIState
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import com.example.plantproject2024.vm.CameraViewModel
import com.example.plantproject2024.vm.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
    context: Context
) {
    var input by remember { mutableStateOf("") }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val ic_search = painterResource(id = R.drawable.ic_search_2)

    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    //Collect value
    val resultState by viewModel.generatedResponse.collectAsState()
    val userState by viewModel.user.collectAsState()
    var UserInfo by rememberSaveable { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result of the activity here
        // For example, you can retrieve data from the activity result
        val data = result.data
        // Handle the data accordingly
    }

    Column {
        Text("Search")
        SearchBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            inputField = {
                SearchBarDefaults.InputField(
                    query = input,
                    onQueryChange = { input = it },
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text("Search") },
                    trailingIcon = { IconButton(onClick = { viewModel.sendPrompt(input) }) {
                        Icon(painter = ic_search, contentDescription = "Search")
                    } }
            ) },
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it}
        ) {
            Text("result")
        }
        //When Click button, launch activity
        Button(onClick = {
            startSearchCamera(context)
        }) {
            Text("Use Camera")
        }

        if( resultState is SearchUIState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (resultState is SearchUIState.Success) {
                result = (resultState as SearchUIState.Success).outputContent
            } else if (resultState is SearchUIState.Error) {
                result = (resultState as SearchUIState.Error).errorMessage
            }
        }
        OutlinedTextField(value = result, onValueChange = { result = it }, enabled = false)
        Button(onClick = { viewModel.getUserInfo() }) {
            Text(text = "Get User Info")
        }


        UserInfo = viewModel.user.collectAsState().value
        Text(text = "UserInfo: $UserInfo")

    }
}

//Perform camera activity
private fun startSearchCamera(
    context: Context
) {
    try{
        var intent = Intent(context, SearchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        Log.i("ActivityStarted", "Search Activity Started")
    } catch (e:Exception) {
        Log.i("ActivityStarted", "startSearchCameraError, $e")
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun SearchScreenPreview() {
    PlantProject2024Theme {
        SearchScreen(navController= NavHostController(LocalContext.current),viewModel = MainViewModel(), LocalContext.current.applicationContext)
    }
}