fun main() {
    val vetor = intArrayOf(5, 3, 2, 4, 7, 1, 0, 6)

    for (i in 0 until vetor.size - 1) {
        var trocou = false
        for (j in 0 until vetor.size - 1 - i) {
            if (vetor[j] > vetor[j + 1]) {
                val temp = vetor[j]
                vetor[j] = vetor[j + 1]
                vetor[j + 1] = temp
                trocou = true
            }
        }
        if (!trocou) break
    }

    println("Vetor ordenado: ${vetor.joinToString()}")
}
