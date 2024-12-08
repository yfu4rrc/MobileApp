package com.example.plantproject2024.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.plantproject2024.R
import com.example.plantproject2024.db.model.samplePlant
import com.example.plantproject2024.screens.ExploreScreen
import com.example.plantproject2024.screens.LoginScreen
import com.example.plantproject2024.screens.MainPlantListScreen
import com.example.plantproject2024.screens.MainScreen
import com.example.plantproject2024.screens.MainSettingScreen
import com.example.plantproject2024.screens.MainTodoScreen
import com.example.plantproject2024.screens.PlantCardScreen
import com.example.plantproject2024.screens.SearchCameraScreen
import com.example.plantproject2024.screens.SearchScreen
import com.example.plantproject2024.screens.SignupScreen
import com.example.plantproject2024.vm.LoginViewModel
import com.example.plantproject2024.vm.MainViewModel
import com.google.firebase.auth.UserInfo


//Main Navigation between main function screens
@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    context: Context,
    currentUser: UserInfo?,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.MainScreen.route,
        modifier = modifier,
    ) {

        composable(Destination.MainScreen.route) {
            MainScreen(navController = navController, context, viewModel, currentUser)
        }
        composable(Destination.SearchCameraScreen.route) {
            SearchCameraScreen(navController = navController, context = navController.context)
        }
        composable(Destination.ExploreScreen.route) {
            ExploreScreen()
        }
        composable(Destination.MainScreen.MainPlantListScreen.route) {
            MainPlantListScreen(navController)
        }
        composable(Destination.SearchScreen.route) {
            SearchScreen(navController = navController, viewModel, context)
        }
        composable(Destination.LoginScreen.route) {
            LoginScreen(navController, viewModel = LoginViewModel(), context, modifier)
        }
        composable(Destination.MainSettingScreen.route) {
            MainSettingScreen()
        }
        composable(Destination.MainScreen.MainTodoScreen.route) {
            MainTodoScreen()
        }
        composable(Destination.MainScreen.MainPlantListScreen.PlantCardScreen.route) { navBackStackEntry ->
            val plant_id: Int? = navBackStackEntry.arguments?.getString("plantId")?.toInt()
            PlantCardScreen(samplePlant)
        }
        composable(Destination.LoginScreen.SignupScreen.route) {
            SignupScreen()
        }
    }
}
