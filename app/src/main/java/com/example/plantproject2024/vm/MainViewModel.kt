package com.example.plantproject2024.vm

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantproject2024.BuildConfig
import com.example.plantproject2024.R
import com.example.plantproject2024.ui.states.SearchUIState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    //Track state of Search UI
    private val _generatedResponse: MutableStateFlow<SearchUIState> = MutableStateFlow(SearchUIState.Initial)
    val generatedResponse: StateFlow<SearchUIState> = _generatedResponse.asStateFlow()

    //Initial status
    init {
        Log.i(TAG, "MainViewModel created")
    }
    //AI vm using Gemini api
    private val generativeModel = GenerativeModel(
        //Use the newest gemini model
        modelName = "gemini-1.5-flash",
        /**
         * Secure with build config, DO NOT USE LITERAL KEY STRING IN CODE
         * Check https://github.com/google/secrets-gradle-plugin for more information
         */
        apiKey = BuildConfig.apiKey
    )

    fun sendPrompt(prompt: String = "Tell me whats your name"): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //Send prompt to gemini, catch response and display
                val response = generativeModel.generateContent(prompt)
                response.text?.let { outputContent ->
                    //Pass output to state value
                    _generatedResponse.value = SearchUIState.Success(outputContent)
                    //Log response easier to track
                    val TAG = "MainViewModel_Response"
                    Log.i(TAG, "Response: $outputContent")
                }
            } catch (e: Exception) {
                    Log.i(TAG, "Error: ${e.message}")
            }
        }
    }


    //Firebase section for user
    private val _user: MutableStateFlow<String> = MutableStateFlow("")
    val user: StateFlow<String> = _user.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val fs_db = Firebase.firestore
            val jason = fs_db.collection("User").document("RZZK0YkvQhrNTM33bHsQ")
            val TAG = "UserInfoFS"

            try {
                var response = jason.get().await()
                if (response != null) {
                    Log.i(TAG, "Response: ${response.data}")
                    _user.value = response.data.toString()
                } else {
                    _user.value = "No response"
                }
            } catch (e: Exception) {
                _user.value = "Error: ${e.message}"
            }
        }
    }
}

    //Search All plants
    //Search my plants
    //Save calendar
    //Plant pics
    //Drag articles
    //Drag user in
    //Save setting options
