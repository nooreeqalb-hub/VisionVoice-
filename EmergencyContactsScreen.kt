package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class EmergencyContact(val name: String, val phone: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactsScreen(onBack: () -> Unit) {
    var contacts by remember { mutableStateOf(listOf(
        EmergencyContact("Family", "+91 98765 43210"),
        EmergencyContact("Friend", "+91 91234 56789")
    ))}
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Contacts") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(contacts) { contact ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(contact.name, style = MaterialTheme.typography.titleMedium)
                            Text(contact.phone, style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = { /* Call intent */ }) { Icon(Icons.Default.Call, null) }
                        IconButton(onClick = { contacts = contacts - contact }) { Icon(Icons.Default.Delete, null) }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Contact") },
                text = {
                    Column {
                        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") })
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (name.isNotBlank() && phone.isNotBlank()) {
                            contacts = contacts + EmergencyContact(name, phone)
                            name = ""; phone = ""; showDialog = false
                        }
                    }) { Text("Save") }
                },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } }
            )
        }
    }
}
