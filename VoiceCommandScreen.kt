package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceCommandScreen(onBack: () -> Unit) {
    var isListening by remember { mutableStateOf(false) }
    var recognizedText by remember { mutableStateOf("Tap mic and say something like 'Read text' or 'Where am I?'") }
    var responseText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voice Assistant") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("You said:", style = MaterialTheme.typography.labelMedium)
                    Text(recognizedText, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(Modifier.height(16.dp))
            if (responseText.isNotEmpty()) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("VisionVoice:", style = MaterialTheme.typography.labelMedium)
                        Text(responseText, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
            FilledIconButton(
                onClick = {
                    isListening = !isListening
                    if (isListening) {
                        recognizedText = "Listening..."
                        // startSpeechRecognizer()
                    } else {
                        recognizedText = "Describe this image"
                        responseText = "Opening Image Description..."
                    }
                },
                modifier = Modifier.size(80.dp)
            ) {
                Icon(Icons.Default.Mic, contentDescription = "Mic", modifier = Modifier.size(40.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(if (isListening) "Listening..." else "Tap to Speak", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(32.dp))
            Divider()
            Spacer(Modifier.height(16.dp))
            Text("Try saying:", style = MaterialTheme.typography.titleSmall)
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("• \"Read this text\"")
                Text("• \"What is in front of me?\"")
                Text("• \"Call emergency contact\"")
                Text("• \"Where am I?\"")
            }
        }
    }
}
