package com.example.to_do_list.presentation.navigation

sealed class Screens(val route:String) {

    object AppNavigation:Screens(route = "appNavigation")
    object UpdateScreen:Screens(route = "updateScreen")
    object homeScreen:Screens(route = "homeScreen")


}
