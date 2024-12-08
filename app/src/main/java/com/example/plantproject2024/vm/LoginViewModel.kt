package com.example.plantproject2024.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.reflect.typeOf

class LoginViewModel: ViewModel() {

    //Firebase section for user
    private val _user: MutableStateFlow<String> = MutableStateFlow("")
    val user: StateFlow<String> = _user.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            var auth: FirebaseAuth = Firebase.auth
            val user = auth.currentUser

            val TAG = "UserInfoFS"

            try {
                var response = user?.uid
                var username = user?.email

                if (response != null) {
                    Log.i(TAG, "Response: ${response}, username: ${username}")
                    _user.value = response.toString()
                } else {
                    _user.value = "No response"
                }
            } catch (e: Exception) {
                _user.value = "Error: ${e.message}"
            }
        }
    }
}