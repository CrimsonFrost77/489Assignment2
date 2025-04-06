package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.assignment2.navigation.NavGraph
import kotlinx.coroutines.launch

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

// 🧱 Main App Setup
@Composable
fun AssignmentApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val menuItems = DrawerDestinations.entries

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                menuItems.forEach { destination ->
                    NavigationDrawerItem(
                        label = { Text(destination.title) },
                        selected = false,
                        onClick = {
                            navController.navigate(destination.route)
                            coroutineScope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        NavGraph(navController = navController, openDrawer = {
            coroutineScope.launch { drawerState.open() }
        })
    }
}
