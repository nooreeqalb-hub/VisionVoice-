package com.example.visionvoice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen() {

    var feedbackText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(4f) }
    var submitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Feedback & Support")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Help us improve VisionVoice",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Your feedback helps us make VisionVoice more accessible and useful for everyone. Share your experience, suggestions, or report any issues you've faced.",
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalDivider()

            Text(
                text = "Rate Your Experience",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "${rating.toInt()} / 5 Stars",
                style = MaterialTheme.typography.bodyLarge
            )

            Slider(
                value = rating,
                onValueChange = { rating = it },
                valueRange = 1f..5f,
                steps = 3
            )

            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                label = {
                    Text("Enter your feedback")
                },
                placeholder = {
                    Text("Tell us what you liked or what can be improved...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                maxLines = 8
            )

            Button(
                onClick = {
                    submitted = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = feedbackText.isNotBlank()
            ) {
                Text("Submit Feedback")
            }

            if (submitted) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "✅ Feedback Submitted",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Thank you for helping us improve VisionVoice. Your feedback has been recorded successfully.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Contact Support",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Email: support@visionvoice.app"
                    )

                    Text(
                        text = "Response Time: Within 24 Hours"
                    )
                }
            }
        }
    }
}
