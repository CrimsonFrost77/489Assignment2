package com.example.assignment2.ui.broadcast

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

enum class BroadcastType(val title: String) {
    Custom("Custom Broadcast Receiver"),
    Battery("System Battery Notification")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastReceiverScreen(navController: NavController, openDrawer: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf<BroadcastType?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Broadcast Receiver") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Wrap dropdown in Box to handle overlap
            Box(modifier = Modifier.fillMaxWidth()) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedType?.title ?: "Select Broadcast Type",
                        onValueChange = {},
                        label = { Text("Broadcast Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        BroadcastType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.title) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    selectedType?.let {
                        when (it) {
                            BroadcastType.Custom -> navController.navigate("custom_input_screen")
                            BroadcastType.Battery -> navController.navigate("battery_receiver_screen")
                        }
                    }
                },
                enabled = selectedType != null
            ) {
                Text("Continue")
            }
        }
    }
}
