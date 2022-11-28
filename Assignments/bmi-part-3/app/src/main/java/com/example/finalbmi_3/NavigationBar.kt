package com.example.finalbmi_3

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController : NavHostController) {
    BottomNavigation (
        // Background color for bottom nav
        backgroundColor = colorResource(id = R.color.green),
        modifier = Modifier.shadow(10.dp)
        ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->

            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title,
                    )
                },
                label = {
                    Text(
                        text = navItem.title,
                        style = MaterialTheme.typography.body2.copy(
                            shadow = Shadow(
                                color = colorResource(id = R.color.green),
                                offset = Offset(0f, 10f),
                                blurRadius = 8f,
                            )
                        ),
                    )
                },
            )
        }    }
}