package com.example.myapp

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SOSButton(
    onSOSClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSOSClick,
        modifier = modifier.size(100.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        shape = androidx.compose.foundation.shape.CircleShape
    ) {
        Text(
            text = "SOS",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EmergencySOSCard(onTriggerSOS: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text("Emergency? Tap SOS", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
            androidx.compose.foundation.layout.Spacer(Modifier.size(12.dp))
            SOSButton(onSOSClick = onTriggerSOS)
            androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp))
            Text("Your location will be sent to emergency contacts", style = MaterialTheme.typography.bodySmall)
        }
    }
}  
