package com.example.to_do_list.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
sealed class NavigationScreens(val route:String, val title:String, val icon:ImageVector){
    object ToDay:NavigationScreens(
        route = "toDay",
        title = "To Day",
        icon = Icons.Default.Home
    )
    object AllTasks:NavigationScreens(
        route = "allTasks",
        title = "All Tasks",
        icon = Icons.Default.List
    )
    object Favorite:NavigationScreens(
        route = "favorite",
        title = "Favorite",
        icon = Icons.Default.Favorite
    )

}

