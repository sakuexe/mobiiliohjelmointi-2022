package com.example.finalbmi_3

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object History : NavRoutes("history")
    object Settings : NavRoutes("settings")
}