package com.hardcodecoder.wallpalette.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hardcodecoder.wallpalette.R
import com.hardcodecoder.wallpalette.ui.home.HomeScreen
import com.hardcodecoder.wallpalette.ui.search.SearchScreen
import com.hardcodecoder.wallpalette.ui.setting.SettingsScreen

sealed class Destination(val id: String, @StringRes val titleResId: Int, val icon: ImageVector) {
    data object Home : Destination("home", R.string.home, Icons.Rounded.Home)
    data object Search : Destination("search", R.string.search, Icons.Rounded.Search)
    data object Settings : Destination("settings", R.string.settings, Icons.Rounded.Settings)
}

@Composable
fun NavigationScreen() {
    val destinations = listOf(
        Destination.Home,
        Destination.Search,
        Destination.Settings
    )
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                destinations = destinations
            ) { destination ->
                navController.navigate(destination.id) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    ) { innerPadding ->
        NavigationHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Destination.Home
        )
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    destinations: List<Destination>,
    navigateTo: (Destination) -> Unit
) {
    NavigationBar(
        modifier = modifier
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination?.route

        destinations.forEach { destination ->
            val label = stringResource(destination.titleResId)
            NavigationBarItem(
                alwaysShowLabel = false,
                selected = destination.id == currentDestination,
                label = {
                    Text(
                        text = label
                    )
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = label
                    )
                },
                onClick = {
                    navigateTo(destination)
                }
            )
        }
    }
}

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.id
    ) {
        composable(Destination.Home.id) {
            HomeScreen(modifier = modifier)
        }

        composable(Destination.Search.id) {
            SearchScreen(modifier = modifier)
        }

        composable(Destination.Settings.id) {
            SettingsScreen(modifier = modifier)
        }
    }
}

@Preview(
    device = "id:pixel_8_pro",
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun NavigationScreenPreview() {
    NavigationScreen()
}