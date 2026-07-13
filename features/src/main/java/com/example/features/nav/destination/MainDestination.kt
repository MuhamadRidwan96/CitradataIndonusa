package com.example.features.nav.destination


sealed class Screen(val route: String) {

    object Splash : Screen("splash")

    //Auth
    object Login : Screen("login")
    object SignUp : Screen("signup")
    //Main Entry
    object Main  : Screen("main")
    //Bottom Navigation
    object Home : Screen("home")
    object Search : Screen("search")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")

    //Profile sub screen
    object EditProfile : Screen("editProfile")
    object Membership : Screen("membership")
    object Terms : Screen("terms")
    object Contact : Screen("contact")
    object Policy : Screen("policy")

    //detail screen
    object Detail : Screen("detail/{projectId}"){
        fun createRoute(projectId:String) = "detail/$projectId"
    }
    //notification
    object Notification : Screen("notification")

}