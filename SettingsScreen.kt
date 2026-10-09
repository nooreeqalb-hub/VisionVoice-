package com.example.myapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    var isVoiceEnabled by remember { mutableStateOf(true) }
    var isVibrationEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Preferences Section
            Text("Preferences", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            
            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Voice Guidance")
                        Switch(checked = isVoiceEnabled, onCheckedChange = { isVoiceEnabled = it })
                    }
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Vibration Feedback")
                        Switch(checked = isVibrationEnabled, onCheckedChange = { isVibrationEnabled = it })
                    }
                }
            }

            Text("Support & Legal", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    ListItem(headlineContent = { Text("Emergency Contacts") }, leadingContent = { Icon(Icons.Default.ContactEmergency, null) }, modifier = Modifier.clickable { onNavigate("emergency") })
                    Divider()
                    ListItem(headlineContent = { Text("SOS History") }, leadingContent = { Icon(Icons.Default.History, null) }, modifier = Modifier.clickable { onNavigate("sos_history") })
                    Divider()
                    ListItem(headlineContent = { Text("Privacy Policy") }, leadingContent = { Icon(Icons.Default.PrivacyTip, null) }, modifier = Modifier.clickable { onNavigate("privacy") })
                    Divider()
                    ListItem(headlineContent = { Text("Terms & Conditions") }, leadingContent = { Icon(Icons.Default.Gavel, null) }, modifier = Modifier.clickable { onNavigate("terms") })
                    Divider()
                    ListItem(headlineContent = { Text("Feedback") }, leadingContent = { Icon(Icons.Default.RateReview, null) }, modifier = Modifier.clickable { onNavigate("feedback") })
                }
            }

            Text("Version 1.0.0 - VisionVoice", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 16.dp))
        }
    }
}
