fun main() {
    var soma = 0

    for (i in 1 until 1000) {
        if (i % 3 == 0 || i % 5 == 0) {
            soma += i
        }
    }

    println("Soma dos múltiplos de 3 ou 5 abaixo de 1000 = $soma")
}
