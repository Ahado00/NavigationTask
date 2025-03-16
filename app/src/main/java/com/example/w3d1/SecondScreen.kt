package com.example.w3d1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


@Composable
fun SecondScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Second Screen")

        Button(onClick = { navController.navigate("thirdScreen") }) {
            Text(text = "Go To Third Screen")
        }

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Go Back")
        }
    }
}
