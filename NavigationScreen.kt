package com.example.myapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        
        composable("home") {
            HomeScreen(onNavigate = { route ->
                navController.navigate(route)
            })
        }
        composable("describe") {
            DescribeImageScreen(onBack = { navController.popBackStack() })
        }
        composable("ocr") {
            OCRScreen(onBack = { navController.popBackStack() })
        }
        composable("detect") {
            ObjectDetectionScreen(onBack = { navController.popBackStack() })
        }
        composable("voice") {
            VoiceCommandScreen(onBack = { navController.popBackStack() })
        }
        composable("emergency") {
            EmergencyContactsScreen(onBack = { navController.popBackStack() })
        }
        composable("feedback") {
            FeedbackScreen(onBack = { navController.popBackStack() })
        }
        composable("about_app") {
            AboutAppScreen(onBack = { navController.popBackStack() })
        }
        composable("about_creator") {
            AboutCreatorScreen(onBack = { navController.popBackStack() })
        }
    }
}
