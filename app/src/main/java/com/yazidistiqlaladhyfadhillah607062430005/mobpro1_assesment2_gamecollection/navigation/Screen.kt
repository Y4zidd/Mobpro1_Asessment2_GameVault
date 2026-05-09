package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.navigation

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object About : Screen("about")
    data object Detail : Screen("detail") {
        fun withId(id: Long) = "detail?id=$id"
    }
}
