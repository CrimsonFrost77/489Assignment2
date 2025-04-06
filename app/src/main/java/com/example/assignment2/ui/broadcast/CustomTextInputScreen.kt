package com.example.assignment2.ui.broadcast

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputScreen(navController: NavController) {
    val context = LocalContext.current
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Custom Broadcast Input") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Enter message to broadcast") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Send the custom broadcast
                    val intent = Intent("com.example.assignment2.CUSTOM_BROADCAST")
                    intent.putExtra("message", message)
                    context.sendBroadcast(intent)

                    // Navigate to screen that receives it
                    navController.navigate("custom_receiver_screen")
                },
                enabled = message.isNotBlank()
            ) {
                Text("Send Broadcast")
            }
        }
    }
}
