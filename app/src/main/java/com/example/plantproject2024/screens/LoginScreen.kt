package com.example.plantproject2024.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.credentials.Credential
import android.credentials.GetCredentialRequest
import android.credentials.GetCredentialResponse
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CustomCredential
import androidx.credentials.provider.AuthenticationAction
import androidx.navigation.NavController
import com.example.plantproject2024.BuildConfig
import com.example.plantproject2024.MainActivity
import com.example.plantproject2024.vm.LoginViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel, context: Context, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.padding(60.dp)) {
        Text("Log in to planty!")
        OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text("Password") })

        Button(onClick = { login(email, password, context, keyboardController) }) { Text(text = "Login")}

        Column {
            Text(text = "Don't have an account?")
            Button(onClick = { navController.navigate("signup") }) { Text(text = "Sign up") }
        }
        Column {
            Text(text = "Or login with")
            Button(onClick = { loginWithGoogle() }) { Text(text = "Google account")}
        }

    }
}

private fun login(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController?
){
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                Log.i("ActivityStarted", "Login Successful")
                // Login successful
                var intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.uid)
                context.startActivity(intent)
            }else{
                // Login failed
                Toast.makeText(context, "Log In Failed, Check your email and password", Toast.LENGTH_SHORT).show()
                Log.i("ActivityStarted", "Login Failed")
            }
            keyboardController?.hide()
        }
}

private fun signup(){

}

private fun loginWithGoogle(){
    val WEB_CLIENT_ID = BuildConfig.WEB_CLIENT_ID
    //Get google id option
    val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
        //.setNonce()
    .build()
    Log.i("ActivityStarted", "Login with Google Started")

}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun handleSignIn(result: GetCredentialResponse) {
    // Handle the successfully returned credential.
    val credential = result.credential

    when (credential) {
        is Credential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // Use googleIdTokenCredential and extract id to validate and
                    // authenticate on your server.
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid google id token response", e)
                }
            }
            else {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }

        else -> {
            // Catch any unrecognized credential type here.
            Log.e(TAG, "Unexpected type of credential")
        }
    }
}