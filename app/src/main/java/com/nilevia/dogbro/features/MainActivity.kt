package com.nilevia.dogbro.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nilevia.dogbro.features.learn.domain.models.Breed
import com.nilevia.dogbro.features.learn.ui.LearnDetailScreen
import com.nilevia.dogbro.features.learn.ui.LearnScreen
import com.nilevia.dogbro.features.quiz.QuizScreen
import com.nilevia.dogbro.utils.route.AppScreen
import com.nilevia.dogbro.utils.route.ROUTE_LEARN_DETAIL
import com.nilevia.dogbro.utils.route.ScreenType
import com.nilevia.dogbro.utils.themes.DogbroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogbroTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val appScreen = AppScreen.getScreenByRoute(currentRoute)
                val showBottomBar = appScreen?.screenType == ScreenType.MAIN_SCREEN
                val showTopBar = appScreen?.screenType == ScreenType.MAIN_SCREEN

                Scaffold(
                    topBar = {
                        if (showTopBar){
                            Column {
                                TopAppBar(
                                    title = { Text(text = "Dog Bro") },
                                )
                                Divider()
                            }
                        }
                    },
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                val items = listOf(
                                    NavItem(AppScreen.Learn.route, "Learn", Icons.Filled.Info),
                                    NavItem(AppScreen.Quiz.route, "Quiz", Icons.Filled.CheckCircle)
                                )
                                items.forEach { item ->
                                    NavigationBarItem(
                                        icon = { Icon(item.icon, contentDescription = item.label) },
                                        label = { Text(item.label) },
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            if (currentRoute != item.route) {
                                                navController.navigate(item.route) {
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Learn.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(AppScreen.Learn.route) {
                            LearnScreen(
                                onBreedSelected = { breed ->
                                    val route = if (breed.subBreed != null) {
                                        "$ROUTE_LEARN_DETAIL/${breed.breed}/${breed.subBreed}"
                                    } else {
                                        "$ROUTE_LEARN_DETAIL/${breed.breed}/none"
                                    }
                                    navController.navigate(route)
                                }
                            )
                        }
                        composable(
                            route = "$ROUTE_LEARN_DETAIL/{breed}/{subBreed}",
                            arguments = listOf(
                                navArgument("breed") { type = NavType.StringType },
                                navArgument("subBreed") {
                                    type = NavType.StringType; defaultValue = "none"
                                }
                            )
                        ) { backStackEntry ->
                            val breedName = backStackEntry.arguments?.getString("breed") ?: ""
                            val subBreedName = backStackEntry.arguments?.getString("subBreed")
                            val breed = if (subBreedName != null && subBreedName != "none") {
                                Breed(breedName, subBreedName)
                            } else {
                                Breed(breedName, null)
                            }
                            LearnDetailScreen(
                                breed = breed,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(AppScreen.Quiz.route) { QuizScreen() }
                    }
                }
            }
        }
    }
}

data class NavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)