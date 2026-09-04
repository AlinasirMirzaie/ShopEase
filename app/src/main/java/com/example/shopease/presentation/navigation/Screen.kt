package com.example.shopease.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object ProductList : Screen("product_list")
    object Categories : Screen("categories")
    object Cart : Screen("cart")

    object Checkout : Screen("checkout")
    object Menu : Screen("menu")
    object Notifications : Screen("notifications")
    object MyProfile : Screen("my_profile")
    object EditProfile : Screen("edit_profile")
    object Orders : Screen("orders")
    object Support : Screen("support")
    object Terms : Screen("terms")
    object AboutUs : Screen("about_us")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object CategoryProducts : Screen("category_products/{category}") {
        fun createRoute(category: String) = "category_products/$category"
    }

    object ChangePassword : Screen("change_password")
    object AccountInfo : Screen("account_info")
    object MyAddresses : Screen("my_addresses")
    object PurchaseExperience : Screen("purchase_experience")
    object Favorites : Screen("favorites")
}