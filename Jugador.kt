data class Jugador(
    val nombre: String,
    val ficha: String
) {
    var estado = ""
    var movimientos: MutableList<List<Int>> = mutableListOf()
}