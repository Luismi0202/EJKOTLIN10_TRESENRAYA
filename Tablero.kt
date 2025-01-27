class Tablero {
    var tablero = hacerTablero()

    private fun hacerTablero(): MutableList<MutableList<String>> {
        var tablero:MutableList<MutableList<String>> = mutableListOf()
        for(columna in 1..3){
            var vector: MutableList<String> = mutableListOf()
            for(fila in 1..3){
                vector.add("  ")
            }
            tablero.add(vector)
        }
        return tablero
    }

    fun mostrarTablero(){
        for(columna in tablero[0].indices){
            print(" ${LETRAS[columna]} ")
        }
        for(i in 0..tablero.size-1){
            println("")
            print("$i")
            for(x in 0..tablero.size-1){
                print(tablero[i][x])
            }
        }
        println(" ")
    }
    companion object{
        val LETRAS = mapOf(0 to "A",1 to "B",2 to "C",3 to "D")
    }

}