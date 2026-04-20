package com.example.a221007_tharssan_nelson_lab04

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.a221007_tharssan_nelson_lab04.ui.theme.BottomNav

@Composable
fun AppNavigation(donorViewModel: DonorViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // show bottom nav if user is logged in
            if (donorViewModel.userType != null && donorViewModel.donorName.isNotBlank()) {
                BottomNav(
                    currentView = currentRoute ?: "home",
                    onNavigate = { route: String -> // Added explicit String type to fix "Cannot infer type"
                        navController.navigate(route)
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = donorViewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable("donate-food") {
                DonateFoodScreen(
                    donorName = donorViewModel.donorName,
                    donorEmail = donorViewModel.donorEmail,
                    onDonate = { donation ->
                        
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("profile") {
                ProfileSettingsScreen(
                    donorName = donorViewModel.donorName,
                    donorEmail = donorViewModel.donorEmail,
                    donorMatric = donorViewModel.donorMatric,
                    onEditProfile = {
                        donorViewModel.clearData()
                        navController.navigate("home")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
