package com.example.plantproject2024.vm

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantproject2024.BuildConfig
import com.example.plantproject2024.ui.states.SearchUIState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

private const val TAG = "CameraViewModel"
class CameraViewModel(): ViewModel() {
    //Track state of Search UI
    private val _generatedResponse: MutableStateFlow<SearchUIState> = MutableStateFlow(SearchUIState.Initial)
    val generatedResponse: StateFlow<SearchUIState> = _generatedResponse.asStateFlow()

    //Initial status
    init {
        Log.i(TAG, "CameraViewModel created")
    }

    //Gemini api
    private val generativeModel = GenerativeModel(
        //Use the newest gemini model
        modelName = "gemini-1.5-flash",
        /**
         * Secure with build config, DO NOT USE LITERAL KEY STRING IN CODE
         * Check https://github.com/google/secrets-gradle-plugin for more information
         */
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
                    val TAG = "CameraViewModel_Response"
                    Log.i(TAG, "Response: $outputContent")

                }
            } catch (e: Exception) {
                Log.i(TAG, "Error: ${e.message}")
            }
        }
    }

}