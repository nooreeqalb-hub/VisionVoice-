package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Vision Voice") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
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
            Text("About Vision Voice", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                "Vision Voice is an accessibility application designed for blind and low-vision individuals. Our mission is to make technology more inclusive by providing simple, voice-friendly, and easy-to-use tools that support independent living. The app focuses on accessibility, usability, and empowerment, helping users interact with information and services confidently.",
                fontSize = 16.sp, lineHeight = 24.sp
            )
            Divider()
            Text("Why I Built This App", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Vision Voice was created to address the everyday challenges faced by blind and visually impaired people. Many applications are not fully accessible, making it difficult for users to complete routine tasks independently.\n\nThis app aims to bridge that gap by offering accessible features such as voice assistance, text recognition, emergency support, and accessibility-focused tools. Our goal is to promote independence, confidence, and equal access to technology for everyone.",
                fontSize = 16.sp, lineHeight = 24.sp
            )
        }
    }
}
