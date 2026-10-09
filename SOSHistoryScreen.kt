package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

data class SOSRecord(val id: String, val timestamp: Long, val lat: Double, val lng: Double, val status: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOSHistoryScreen(onBack: () -> Unit) {
    var history by remember { mutableStateOf<List<SOSRecord>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Load from Firebase / Room
        history = listOf(
            SOSRecord("1", System.currentTimeMillis() - 3600000, 28.6139, 77.2090, "Sent to 2 contacts"),
            SOSRecord("2", System.currentTimeMillis() - 86400000, 28.6139, 77.2090, "Sent to 2 contacts")
        )
    }

    Scaffold(topBar = { TopAppBar(title = { Text("SOS History") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }) }) { padding ->
        if (history.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No SOS history yet")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(history) { record ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(record.timestamp)), style = MaterialTheme.typography.titleSmall)
                                Spacer(Modifier.height(4.dp))
                                Row {
                                    Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("${record.lat}, ${record.lng}", style = MaterialTheme.typography.bodySmall)
                                }
                                Text(record.status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}
