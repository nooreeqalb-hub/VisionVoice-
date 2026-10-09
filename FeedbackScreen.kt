package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(onBack: () -> Unit) {
    var feedback by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0f) }
    var showThanks by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feedback") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("We value your feedback!", style = MaterialTheme.typography.titleLarge)
            Text("Help us improve VisionVoice for everyone.", style = MaterialTheme.typography.bodyMedium)

            Text("Rate your experience:")
            Slider(value = rating, onValueChange = { rating = it }, valueRange = 0f..5f, steps = 4)
            Text("${rating.toInt()} / 5 Stars")

            OutlinedTextField(
                value = feedback,
                onValueChange = { feedback = it },
                label = { Text("Your feedback...") },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                maxLines = 5
            )

            Button(
                onClick = { showThanks = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = feedback.isNotBlank()
            ) {
                Text("Submit Feedback")
            }

            if (showThanks) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(
                        "Thank you Rosh! Your feedback has been submitted successfully.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}  
