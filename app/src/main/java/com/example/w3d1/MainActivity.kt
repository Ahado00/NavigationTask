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


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            var title by remember { mutableStateOf("First Screen") } // Default title

            W3D1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { DynamicTopAppBar(title, navController) } // Pass title dynamically
                ) { innerPadding ->
                    NavigationHost(innerPadding, navController, setTitle = { newTitle ->
                        title = newTitle // Update title dynamically
                    })
                }
            }
        }
    }
}


@Composable
fun NavigationHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    setTitle: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "firstScreen",
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable("firstScreen") {
            setTitle("First Screen") // Update title
            FisrtScreen(navController)
        }
        composable("secondScreen") {
            setTitle("Second Screen") // Update title
            SecondScreen(navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicTopAppBar(title: String, navController: NavHostController) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) { // Show back button only if needed
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






