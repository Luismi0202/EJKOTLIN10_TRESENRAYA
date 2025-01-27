class Juego(
    var tablerojuego:Tablero,
    val jugador1: Jugador,
    val jugador2: Jugador
) {
    fun inicio(){
        jugador1.estado = "Esperando..."
        jugador2.estado = "Atacando..."
        var jugadoractivo = jugador2
        do{
            jugadoractivo = cambioTurno(jugador1,jugador2)
            println("TURNO DE ${jugadoractivo.nombre} FICHA: ${jugadoractivo.ficha}")
            tablerojuego.mostrarTablero()
            colocarFicha(jugadoractivo)

        }while(!empate() && !ganador(jugadoractivo))
    }

    fun preguntar(msj: String): Boolean {
        var respuestaValida = false
        var seguir = false
        do {
            print("$msj (s/n): ")
            val respuesta = readln().trim().lowercase()
            when (respuesta) {
                "s" -> {
                    seguir = true
                    respuestaValida = true
                }
                "n" -> {
                    seguir = false
                    respuestaValida = true
                }
                else->{
                    println("Respuesta no válida! Inténtelo de nuevo...")
                }
            }
        } while (!respuestaValida)
        return seguir
    }

    fun mostrarResultados():String{
        var mensaje = ""
        if(empate()){
            mensaje = """
                XOXOXO
                EMPATE
                XOXOXO
                
                O los dos sois muy buenos o los dos sois muy malos...
            """.trimIndent()
        }
        else if(ganador(jugador1)){
            mensaje = """
                XOXOXO
                GANA ${jugador1.nombre}
                XOXOXO
                ¡Bien hecho! Y tú (${jugador2.nombre})... ¡No te rindas!
            """.trimIndent()
        }

        else if(ganador(jugador2)){
            mensaje= """
                XOXOXO
                GANA ${jugador2.nombre}
                XOXOXO
                ¡Bien hecho! Y tu (${jugador1.nombre})... ¡No te rindas!
            """.trimIndent()
        }
        return mensaje
    }

    private fun comprobarHorizontal(filas: List<Int>, columnas: List<Int>):Boolean{
        var horizontal = true

        if(filas.size > 1){
            horizontal = false
        }

        else if(columnas.size <= 2){
            horizontal = false
        }

        else{
            var siguienteColumna = 0
            for(columna in columnas){
                if(columna +1 != ++siguienteColumna){
                    horizontal = false
                }
            }
        }
        return horizontal
    }

    private fun comprobarVertical(filas: List<Int>, columnas: List<Int>):Boolean{
        var vertical = true

        if(columnas.size > 1){
            vertical = false
        }

        else if(filas.size <= 2){
            vertical = false
        }
        else {
            var siguienteFila = 0
            for (fila in filas) {
                if (fila + 1 != ++siguienteFila) {
                    vertical = false
                }
            }
        }
        return vertical
    }

    private fun comprobarDiagonal(filas: Set<Int>, columnas: Set<Int>):Boolean{
        var diagonal = true

        if(filas.size < tablerojuego.tablero.size){
            diagonal = false
        }

        else if(columnas.size < tablerojuego.tablero[0].size){
            diagonal = false
        }

        else if(filas.size != columnas.size){
            diagonal = false
        }

        return diagonal
    }

    private fun hayFicha(fila:Int, columna:Int):Boolean{
        return tablerojuego.tablero[fila][columna] == " X " || tablerojuego.tablero[fila][columna] == " O "
    }

    private fun colocarFicha(jugadoractivo: Jugador) {
        var posicionValida = false
        do{
            try{
                println("Dame la posición en formato (X,Y)")
                print(">> ")
                var posiciones = readln().split(',')

                if(posiciones.size < 2){
                    throw Exception("Tienes que poner una letra/numero y un número (X,Y)")
                }
                var filaStr = posiciones[1]
                var columnaStr = posiciones[0]

                var filaInt = 0
                var columnaInt: Int? = 0

                if(columnaStr.uppercase() in LETRAS){
                    columnaInt = LETRAS[columnaStr.uppercase()]
                    filaInt = filaStr.toInt()
                }
                else{
                    filaInt = filaStr.toInt()
                    columnaInt = columnaStr.toInt()
                }

                //PONGO EL !! PORQUE YA ME HE ASEGURADO DE QUE ESTÉ EN LETRAS, COSAS DE KOTLIN. PREGUNTAR.

                if(filaInt >= tablerojuego.tablero.size || columnaInt!! >= tablerojuego.tablero[0].size){
                    throw Exception("Los números introducidos se salen del rango del tablero.")
                }

                if(hayFicha(filaInt,columnaInt)){
                    throw Exception("Posición ($columnaStr,$filaInt) ocupada")
                }

                else{
                    jugadoractivo.movimientos.add(listOf(columnaInt,filaInt))
                    tablerojuego.tablero[filaInt][columnaInt] = " ${jugadoractivo.ficha} "
                    posicionValida = true
                }
            }catch (e: Exception){
                println("¡Error! Detalle: ${e.message}. Pruebe de nuevo.")
            }
        }while(!posicionValida)
        println("¡Ficha colocada con éxito!")
    }

    private fun ganador(jugadoractivo:Jugador):Boolean{
        var partidaFinalizada = false

        var filas: MutableSet<Int> = mutableSetOf()
        var columnas: MutableSet<Int> = mutableSetOf()
        for(movimientos in jugadoractivo.movimientos){
            filas.add(movimientos[1])
            columnas.add(movimientos[0])
        }
        val filasOrdenadas = filas.sorted()
        val columnasOrdenadas = columnas.sorted()

        if(comprobarHorizontal(filasOrdenadas,columnasOrdenadas)){
            partidaFinalizada = true
        }

        if(comprobarVertical(filasOrdenadas,columnasOrdenadas)){
            partidaFinalizada = true
        }

        if(comprobarDiagonal(filas,columnas)){
            partidaFinalizada = true
        }
        return partidaFinalizada
    }

    private fun empate():Boolean{
        var partidaFinalizada = true
        for(fila in 0..tablerojuego.tablero.size -1){
            for(columna in 0..tablerojuego.tablero[0].size-1){
                if(tablerojuego.tablero[fila][columna] == "  "){
                    partidaFinalizada = false
                }
            }
        }
        return partidaFinalizada
    }

    private fun cambioTurno(jugador1: Jugador, jugador2: Jugador):Jugador {
            if (jugador1.estado == "Atacando") {
                jugador1.estado = "Esperando..."
                jugador2.estado = "Atacando"
                return jugador2
            } else {
                jugador1.estado = "Atacando"
                jugador2.estado = "Esperando..."
                return jugador1
            }
        }
    companion object{
        val LETRAS = mapOf("A" to 0,"B" to 1,"C" to 2,"D" to 3)
    }
}

