package com.example.shopease.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopease.presentation.auth.ForgotPasswordScreen
import com.example.shopease.presentation.auth.LoginScreen
import com.example.shopease.presentation.auth.RegisterScreen
import com.example.shopease.presentation.cart.CartScreen
import com.example.shopease.presentation.category.CategoriesScreen
import com.example.shopease.presentation.category.CategoryProductsScreen
import com.example.shopease.presentation.checkout.CheckoutScreen
import com.example.shopease.presentation.components.BottomNavItem
import com.example.shopease.presentation.menu.MenuScreen
import com.example.shopease.presentation.notifications.NotificationsScreen
import com.example.shopease.presentation.orders.OrdersScreen
import com.example.shopease.presentation.product.ProductDetailScreen
import com.example.shopease.presentation.product.ProductListScreen
import com.example.shopease.presentation.profile.AccountInfoScreen
import com.example.shopease.presentation.profile.ChangePasswordScreen
import com.example.shopease.presentation.profile.EditProfileScreen
import com.example.shopease.presentation.profile.FavoritesScreen
import com.example.shopease.presentation.profile.MyAddressesScreen
import com.example.shopease.presentation.profile.ProfileScreen
import com.example.shopease.presentation.profile.PurchaseExperienceScreen
import com.example.shopease.presentation.splash.SplashScreen
import com.example.shopease.presentation.static.AboutUsScreen
import com.example.shopease.presentation.static.SupportScreen
import com.example.shopease.presentation.static.TermsScreen

private data class BottomItem(
    val screen: Screen,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val bottomItems = listOf(
    BottomItem(Screen.ProductList, "خانه", Icons.Default.Home),
    BottomItem(Screen.Categories, "دسته‌بندی", Icons.Default.Category),
    BottomItem(Screen.Cart, "سبد خرید", Icons.Default.ShoppingCart),
    BottomItem(Screen.MyProfile, "پروفایل من", Icons.Default.Person)
)

private val routesWithoutBottomBar = setOf(
    Screen.Splash.route,
    Screen.Login.route,
    Screen.Register.route,
    Screen.ForgotPassword.route
)

@Composable
fun ShopEaseNavGraph() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        bottomBar = {
            if (currentDestination?.route !in routesWithoutBottomBar) {
                Surface(
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        bottomItems.forEach { item ->
                            if (item.screen == Screen.MyProfile) {
                                BottomNavItem(
                                    selected = currentDestination?.route == item.screen.route,
                                    onClick = {
                                        navController.navigate(item.screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    label = item.label,
                                    iconContent = {
                                        com.example.shopease.presentation.components.ProfileIcon(
                                            size = 24.dp
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                BottomNavItem(
                                    selected = currentDestination?.route == item.screen.route,
                                    onClick = {
                                        navController.navigate(item.screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = item.icon,
                                    label = item.label,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToMain = {
                        navController.navigate(Screen.ProductList.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.ProductList.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                    onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Screen.ProductList.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }

            composable(Screen.ForgotPassword.route) {
                ForgotPasswordScreen(onBackClick = { navController.popBackStack() })
            }

            composable(Screen.ProductList.route) {
                ProductListScreen(
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onCategoryClick = { category ->
                        navController.navigate(
                            Screen.CategoryProducts.createRoute(
                                Uri.encode(
                                    category
                                )
                            )
                        )
                    },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) }
                )
            }

            composable(Screen.Categories.route) {
                CategoriesScreen(
                    onCategoryClick = { category ->
                        navController.navigate(
                            Screen.CategoryProducts.createRoute(
                                Uri.encode(
                                    category
                                )
                            )
                        )
                    },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onBackClick = { navController.popBackStack() },
                )
            }

            composable(Screen.Cart.route) {
                CartScreen(
                    onCheckoutClick = { navController.navigate(Screen.Checkout.route) },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onBackClick = { navController.popBackStack() },

                    )
            }

            composable(Screen.Checkout.route) {
                CheckoutScreen(
                    onBackClick = { navController.popBackStack() },
                    onOrderPlaced = {
                        navController.navigate(Screen.Orders.route) {
                            popUpTo(Screen.Cart.route) { inclusive = true }
                        }
                    },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.Menu.route) {
                MenuScreen(
                    onMyProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onOrdersClick = { navController.navigate(Screen.Orders.route) },
                    onSupportClick = { navController.navigate(Screen.Support.route) },
                    onTermsClick = { navController.navigate(Screen.Terms.route) },
                    onAboutUsClick = { navController.navigate(Screen.AboutUs.route) },
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.Notifications.route) {
                NotificationsScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.MyProfile.route) {
                ProfileScreen(
                    onEditProfileClick = { navController.navigate(Screen.EditProfile.route) },
                    onOrdersClick = { navController.navigate(Screen.Orders.route) },
                    onPurchaseExperienceClick = { navController.navigate(Screen.PurchaseExperience.route) },
                    onChangePasswordClick = { navController.navigate(Screen.ChangePassword.route) },
                    onAccountInfoClick = { navController.navigate(Screen.AccountInfo.route) },
                    onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                    onAddressesClick = { navController.navigate(Screen.MyAddresses.route) },
                    onLoggedOut = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.ChangePassword.route) {
                ChangePasswordScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.AccountInfo.route) {
                AccountInfoScreen(onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },)
            }

            composable(Screen.MyAddresses.route) {
                MyAddressesScreen(onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },)
            }

            composable(Screen.PurchaseExperience.route) {
                PurchaseExperienceScreen(onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },)
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(Screen.EditProfile.route) {
                EditProfileScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    )
            }

            composable(Screen.Orders.route) {
                OrdersScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.Support.route) {
                SupportScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) }
                )
            }

            composable(Screen.Terms.route) {
                TermsScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(Screen.AboutUs.route) {
                AboutUsScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                )
            }

            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) {
                ProductDetailScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(
                route = Screen.CategoryProducts.route,
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) {
                CategoryProductsScreen(
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }


            composable(Screen.Cart.route) {
                CartScreen(
                    onCheckoutClick = { navController.navigate(Screen.Checkout.route) },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) },
                    onBackClick = { navController.popBackStack() },

                    )
            }

            composable(Screen.Checkout.route) {
                CheckoutScreen(
                    onBackClick = { navController.popBackStack() },
                    onOrderPlaced = {
                        navController.navigate(Screen.Orders.route) {
                            popUpTo(Screen.Cart.route) { inclusive = true }
                        }
                    },
                    onMenuClick = { navController.navigate(Screen.Menu.route) },
                    onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                    onProfileClick = { navController.navigate(Screen.MyProfile.route) }
                )
            }

        }
    }
}