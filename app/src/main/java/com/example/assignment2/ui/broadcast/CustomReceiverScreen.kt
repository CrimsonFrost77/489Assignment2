package com.example.assignment2.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomReceiverScreen() {
    val context = LocalContext.current
    var receivedMessage by remember { mutableStateOf<String?>(null) }

    // BroadcastReceiver logic
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "com.example.assignment2.CUSTOM_BROADCAST") {
                    receivedMessage = intent.getStringExtra("message")
                }
            }
        }

        val filter = IntentFilter("com.example.assignment2.CUSTOM_BROADCAST")
        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        // Cleanup on disposal
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Custom Broadcast Receiver") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = receivedMessage?.let { "Received Message:\n\n$it" } ?: "Waiting for broadcast...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
