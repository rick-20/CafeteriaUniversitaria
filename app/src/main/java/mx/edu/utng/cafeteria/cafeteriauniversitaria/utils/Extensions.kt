package mx.edu.utng.cafeteria.cafeteriauniversitaria.utils

import java.text.NumberFormat
import java.util.*

fun Double.toMoneyFormat(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
    return format.format(this)
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}