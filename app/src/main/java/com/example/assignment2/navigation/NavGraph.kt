package com.example.assignment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment2.ui.broadcast.BroadcastReceiverScreen
import com.example.assignment2.ui.broadcast.CustomTextInputScreen
import com.example.assignment2.ui.broadcast.BatteryReceiverScreen
import com.example.assignment2.ui.image.ImageScaleScreen
import com.example.assignment2.ui.video.VideoPlayerScreen
import com.example.assignment2.ui.audio.AudioPlayerScreen
import com.example.assignment2.DrawerDestinations
import com.example.assignment2.ui.broadcast.CustomReceiverScreen

@Composable
fun NavGraph(navController: NavHostController, openDrawer: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = DrawerDestinations.Broadcast.route
    ) {
        // 🧭 Drawer screens
        composable(DrawerDestinations.Broadcast.route) {
            BroadcastReceiverScreen(navController = navController, openDrawer = openDrawer)
        }
        composable(DrawerDestinations.Image.route) {
            ImageScaleScreen(openDrawer = openDrawer)
        }
        composable(DrawerDestinations.Video.route) {
            VideoPlayerScreen(openDrawer = openDrawer)
        }
        composable(DrawerDestinations.Audio.route) {
            AudioPlayerScreen(openDrawer = openDrawer)
        }

        // 🎯 Sub-navigation from Broadcast
        composable("custom_input_screen") {
            CustomTextInputScreen(navController = navController)
        }
        composable("battery_receiver_screen") {
            BatteryReceiverScreen(navController = navController)
        }
        composable("custom_receiver_screen") {
            CustomReceiverScreen()
        }

    }
}
