package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.navegation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ProductoDetalle : Screen("producto/{productoId}") {
        fun createRoute(productoId: String) = "producto/$productoId"
    }
    object Carrito : Screen("carrito")
    object Pedidos : Screen("pedidos")
    object Perfil : Screen("perfil")

    // Admin
    object AdminHome : Screen("admin/home")
    object ProductosCrud : Screen("admin/productos")
    object PromocionesAdmin : Screen("admin/promociones")
    object PedidosAdmin : Screen("admin/pedidos")
}
