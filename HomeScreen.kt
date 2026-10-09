package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HomeFeature(val title: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val features = listOf(
        HomeFeature("Describe Image", Icons.Default.Visibility, "describe"),
        HomeFeature("Read Text", Icons.Default.TextFields, "ocr"),
        HomeFeature("Detect Object", Icons.Default.Search, "detect"),
        HomeFeature("Voice Assistant", Icons.Default.Mic, "voice"),
        HomeFeature("Emergency Contacts", Icons.Default.ContactEmergency, "emergency"),
        HomeFeature("Feedback", Icons.Default.Feedback, "feedback"),
        HomeFeature("About App", Icons.Default.Info, "about_app"),
        HomeFeature("About Creator", Icons.Default.Person, "about_creator")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("VisionVoice", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Welcome to VisionVoice", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("Your AI-powered eyes. Tap any feature to start.", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(features.size) { index ->
                    val feature = features[index]
                    Card(
                        onClick = { onNavigate(feature.route) },
                        modifier = Modifier.fillMaxWidth().height(120.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(feature.icon, contentDescription = null, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(feature.title, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
