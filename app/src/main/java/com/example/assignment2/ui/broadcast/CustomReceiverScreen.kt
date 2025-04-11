package com.example.assignment2.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomReceiverScreen(message: String) {
    val context = LocalContext.current
    var receivedMessage by remember { mutableStateOf<String?>(null) }

    // ✅ Register dynamic broadcast receiver securely
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "com.example.assignment2.CUSTOM_BROADCAST") {
                    val received = intent.getStringExtra("message")
                    receivedMessage = received
                }
            }
        }

        val filter = IntentFilter("com.example.assignment2.CUSTOM_BROADCAST")
        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED // ✅ this fixes the API 33+ error
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    // ✅ Send broadcast after receiver is registered
    LaunchedEffect(message) {
        delay(200)

        val intent = Intent("com.example.assignment2.CUSTOM_BROADCAST")
        intent.setPackage(context.packageName) // ✅ Makes it explicit to your app only
        intent.putExtra("message", message)

        context.sendBroadcast(intent)
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

