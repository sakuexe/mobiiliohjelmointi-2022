package com.example.finalbmi_3

sealed class NavRoutes(val route: String) {
    // sealed class is a class that can't be instantiated
    // this is done to make the bottom navigation work
    object Home : NavRoutes("home")
    object History : NavRoutes("history")
    object Settings : NavRoutes("settings")
}