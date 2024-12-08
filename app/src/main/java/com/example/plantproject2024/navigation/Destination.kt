package com.example.plantproject2024.navigation

sealed class Destination(val route: String){
    //Screens
    data object MainScreen: Destination("mainScreen"){
        data object MainPlantListScreen: Destination("mainPlantListScreen"){
            data object PlantCardScreen: Destination("plantCardScreen/{plantId}")
        }
        data object MainTodoScreen: Destination("mainTodoScreen")
    }
    data object SearchCameraScreen: Destination("searchCameraScreen")
    data object ExploreScreen: Destination("exploreScreen")
    data object LoginScreen: Destination("loginScreen"){
        data object SignupScreen: Destination("signupScreen")
    }

    data object MainSettingScreen: Destination("mainSettingScreen")

    data object SearchScreen: Destination("searchScreen")

}
