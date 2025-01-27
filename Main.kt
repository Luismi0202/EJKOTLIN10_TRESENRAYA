import kotlin.math.E


fun limpiarPantalla(){
    repeat(50){ println(" ") }
}

fun mostrarMenu(){
    println("""
        XOXOXOXOXOXOX
        TRES EN RAYAS
        XOXOXOXOXOXOX
        
        1.- Comenzar partida en local
        2.- Salir
    """.trimIndent())
}

fun pedirNombre(msj:String):String{
    println("$msj dame tu nombre")
    print(">> ")
    return readln()
}

fun crearJugador(nombre:String,ficha:String):Jugador{
    return Jugador(nombre,ficha)
}

fun inicializarPartida(jugador1:Jugador,jugador2:Jugador):Juego{
    val tablero = Tablero()
    return Juego(tablero,jugador1,jugador2)
}

fun partidaLocal(){
    var seguirJugando = false
    val nombrej1 = pedirNombre("Jugador 1")
    val nombrej2 = pedirNombre("Jugador 2") //PIDO EL NOMBRE Y LUEGO INSTANCIO A JUGADOR PARA QUE CUANDO SE REPITA, NO PILLE LOS MOVIMIENTOS DE LA ANTERIOR PARTIDA
    do{
        val jugador1 = crearJugador(nombrej1,"X")
        val jugador2 = crearJugador(nombrej2,"O")
        limpiarPantalla()
        val partida = inicializarPartida(jugador1,jugador2)

        partida.inicio()
        limpiarPantalla()
        
        println(partida.mostrarResultados())
        seguirJugando = partida.preguntar("¿Deseas seguir jugando?")
    }while(seguirJugando)

}

fun elegirOpcion():Int{
    var opcion:Int? = null
    do{
        try{
            print(">> ")
            opcion = readln().toInt()
            if(opcion <=0 || opcion >2 ){
                throw IllegalArgumentException("Número fuera de rango")
            }
        }catch(e: IllegalArgumentException){
            println("¡Error! Número inválido Detalle: ${e.message}")
        }
    }while(opcion == null)
    return opcion
}

fun main(){
    var opcion = 0
    do{
        limpiarPantalla()
        mostrarMenu()
        opcion = elegirOpcion()
        when(opcion){
            1-> partidaLocal()
        }
    }while(opcion != 2)
}