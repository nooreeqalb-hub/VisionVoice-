package com.example.visionvoice

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen() {
    var feedbackText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(3f) }
    var isSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Feedback") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Aapka feedback hamare liye important hai. VisionVoice ko behtar banane me madad karein.",
                style = MaterialTheme.typography.bodyLarge
            )

            Text("Rating: ${rating.toInt()} / 5", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = rating,
                onValueChange = { rating = it },
                valueRange = 1f..5f,
                steps = 3
            )

            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                label = { Text("Aapka Feedback") },
                placeholder = { Text("Yahan likhein...") },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                maxLines = 5
            )

            Button(
                onClick = { isSubmitted = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = feedbackText.isNotEmpty()
            ) {
                Text("Submit Feedback")
            }

            if (isSubmitted) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(
                        "Thank you! Aapka feedback save ho gaya hai.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}      
