package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms & Conditions") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            Text("Last Updated: May 10, 2026", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(16.dp))
            Text("Welcome to VisionVoice", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "By using this app, you agree to these terms. This app is designed to assist visually impaired users with AI-powered scene description, text reading, object detection, and emergency SOS.\n\n" +
                "1. Use at Your Own Risk: AI descriptions may not be 100% accurate. Always verify critical information.\n\n" +
                "2. Emergency Feature: SOS sends SMS with location to your contacts. It is not a replacement for calling emergency services directly (112/911).\n\n" +
                "3. Permissions: You must grant camera, microphone, and location permissions for core features to work.\n\n" +
                "4. No Misuse: Do not use the app to harm, stalk, or violate privacy of others.\n\n" +
                "5. Intellectual Property: The app design, logo, and code belong to VisionVoice team.\n\n" +
                "6. Limitation of Liability: We are not liable for any damages arising from use of the app.\n\n" +
                "For full details contact support@visionvoice.app"
            )
        }
    }
}
