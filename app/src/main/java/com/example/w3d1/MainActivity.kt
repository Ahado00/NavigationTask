package com.example.w3d1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.w3d1.ui.theme.W3D1Theme

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            var title by remember { mutableStateOf("Home") }
            var currentScreen by remember { mutableStateOf("firstScreen") }

            W3D1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { DynamicTopAppBar(title, navController) },
                    bottomBar = { BottomNavigationBar(navController, currentScreen) } // Add BottomNavigationBar
                ) { innerPadding ->
                    NavigationHost(
                        innerPadding,
                        navController,
                        setTitle = { newTitle -> title = newTitle },
                        setCurrentScreen = { screen -> currentScreen = screen }
                    )
                }
            }
        }
    }
}


@Composable
fun NavigationHost(innerPadding: PaddingValues, navController: NavHostController, setTitle: (String) -> Unit, setCurrentScreen: (String) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = "firstScreen",
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable("firstScreen") {
            setTitle("Home")
            setCurrentScreen("firstScreen")
            FisrtScreen(navController)
        }
        composable("secondScreen") {
            setTitle("Profile")
            setCurrentScreen("secondScreen")
            SecondScreen(navController)
        }
        composable("thirdScreen") {
            setTitle("Settings")
            setCurrentScreen("thirdScreen")
            ThirdScreen(navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicTopAppBar(title: String, navController: NavHostController) {

    val currentBackStackEntry = navController.currentBackStackEntry
    val showBackButton = currentBackStackEntry?.destination?.route != "firstScreen"

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) { // Show back button if not on first screen
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More Options"
                )
            }
        }
    )
}




@Composable
fun BottomNavigationBar(navController: NavHostController, currentScreen: String) {
    val items = listOf(
        BottomNavItem("firstScreen", "Home", Icons.Filled.Home),
        BottomNavItem("secondScreen", "Profile", Icons.Filled.Person),
        BottomNavItem("thirdScreen", "Settings", Icons.Filled.Settings)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentScreen == item.route,
                onClick = {
                    if (currentScreen != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

