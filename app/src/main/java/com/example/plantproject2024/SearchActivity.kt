package com.example.plantproject2024

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.plantproject2024.screens.SearchCameraScreen
import com.example.plantproject2024.screens.getActivity
import com.example.plantproject2024.ui.theme.PlantProject2024Theme

class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantProject2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context: Context = applicationContext
                    Log.i("SearchActivityStarted", "onCreate")
                    if(permissionCheck()){
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
                        startCamera()
                    } else{
                        requestPermission()
                    }

                    Log.i("ActivityStarted", "SearchCameraScreen ended")
                }
            }
        }
    }
    //Request permission
    fun requestPermission() {
        try {
            ActivityCompat.requestPermissions(
                this , REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show()
            Log.i("SearchActivityStarted", "requestPermission granted")
        } catch (e: Exception) {
            Log.i("SearchActivityStarted", "requestPermission error: $e")
        }
    }

    //Request permission
    @Composable
    private fun startCamera() {
        var cameraController: CameraController = LifecycleCameraController(this)
        var cameraProvider: ProcessCameraProvider = ProcessCameraProvider.getInstance(this).get()
        //bind camera to lifecycle
        //cameraController.bindToLifecycle(this)
        //Use back camera by default
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        //val previewView =
        Surface(
        ){
            //Launch search camera
            SearchCameraScreen(navController = rememberNavController(), context = LocalContext.current)
        }
        Log.i("SearchActivityStarted", "startCamera")
    }

    //Check all permission granted
    fun permissionCheck(): Boolean = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}



@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    PlantProject2024Theme {

    }
}