package com.example.assignment2.ui.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assignment2.DrawerDestinations
import kotlinx.coroutines.launch

@Composable
fun DrawerScaffold(
    navController: NavController,
    content: @Composable (openDrawer: () -> Unit) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                DrawerDestinations.entries.forEach { destination ->
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
        content {
            coroutineScope.launch { drawerState.open() }
        }
    }
}