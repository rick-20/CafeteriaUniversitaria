package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth.LoginScreen
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth.RegisterScreen
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario.*
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.admin.*
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthState
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    val startDestination = when (authState) {
        is AuthState.Authenticated -> {
            if (currentUser?.rol == "admin") Screen.AdminHome.route else Screen.Home.route
        }
        else -> Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth Screens
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { usuario ->
                    if (usuario.rol == "admin") {
                        navController.navigate(Screen.AdminHome.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // Usuario Screens
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToProducto = { productoId ->
                    navController.navigate(Screen.ProductoDetalle.createRoute(productoId))
                },
                onNavigateToCarrito = { navController.navigate(Screen.Carrito.route) },
                onNavigateToPedidos = { navController.navigate(Screen.Pedidos.route) },
                onNavigateToPerfil = { navController.navigate(Screen.Perfil.route) }
            )
        }

        composable(
            route = Screen.ProductoDetalle.route,
            arguments = listOf(navArgument("productoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
            ProductoDetalleScreen(
                productoId = productoId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Carrito.route) {
            CarritoScreen(
                onNavigateBack = { navController.popBackStack() },
                onFinalizarCompra = { navController.navigate(Screen.Pedidos.route) }
            )
        }

        composable(Screen.Pedidos.route) {
            PedidosScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Admin Screens
        composable(Screen.AdminHome.route) {
            AdminHomeScreen(
                onNavigateToProductos = { navController.navigate(Screen.ProductosCrud.route) },
                onNavigateToPromociones = { navController.navigate(Screen.PromocionesAdmin.route) },
                onNavigateToPedidos = { navController.navigate(Screen.PedidosAdmin.route) },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ProductosCrud.route) {
            ProductosCrudScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PromocionesAdmin.route) {
            PromocionesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PedidosAdmin.route) {
            PedidosAdminScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}