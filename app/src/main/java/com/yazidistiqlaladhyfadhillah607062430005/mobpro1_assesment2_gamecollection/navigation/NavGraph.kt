package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main.MainScreen
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.detail.DetailScreen
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.about.AboutScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            DetailScreen(navController = navController, id = id)
        }
    }
}
