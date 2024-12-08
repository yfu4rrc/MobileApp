package com.example.plantproject2024.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plantproject2024.R
import com.example.plantproject2024.navigation.Destination
import com.example.plantproject2024.ui.theme.PlantProject2024Theme

@Composable
fun Bottombar (navController: NavController) {
    NavigationBar {
        val navBackStackENtry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackENtry.value?.destination
        val ic_home = painterResource(id = R.drawable.ic_home)
        val ic_search = painterResource(id = R.drawable.ic_search)
        val ic_explore = painterResource(id = R.drawable.ic_explore)

        NavigationBarItem(
            icon = { Icon(painter = ic_home, contentDescription = "Home Screen Icon") },
            label = { Text(text = "Home") },
            selected = currentDestination?.route == Destination.MainScreen.route,
            onClick = { navController.navigate(Destination.MainScreen.route) },
            modifier = Modifier.size(24.dp)
        )
        NavigationBarItem(
            icon = { Icon(painter = ic_search, contentDescription = "Search Screen Icon") },
            label = { Text(text = "Search") },
            selected = currentDestination?.route == Destination.SearchScreen.route,
            onClick = { navController.navigate(Destination.SearchScreen.route) },
            modifier = Modifier.size(24.dp)
        )
        NavigationBarItem(
            icon = { Icon(painter = ic_explore, contentDescription = "Explore Screen Icon") },
            label = { Text(text = "Explore") },
            selected = currentDestination?.route == Destination.ExploreScreen.route,
            onClick = { navController.navigate(Destination.ExploreScreen.route) },
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun BottombarPreview() {
    PlantProject2024Theme {
        Bottombar(navController = rememberNavController())
    }
}