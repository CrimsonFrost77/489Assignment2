package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.assignment2.navigation.NavGraph
import com.example.assignment2.ui.drawer.DrawerScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentApp()
        }
    }
}

// 🧭 Drawer destinations
enum class DrawerDestinations(val route: String, val title: String) {
    Broadcast("broadcast", "Broadcast Receiver"),
    Image("image", "Image Scale"),
    Video("video", "Video Player"),
    Audio("audio", "Audio Player")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssignmentApp() {
    val navController = rememberNavController()

    DrawerScaffold(navController = navController) { openDrawer ->
        NavGraph(navController = navController, openDrawer = openDrawer)
    }
}


