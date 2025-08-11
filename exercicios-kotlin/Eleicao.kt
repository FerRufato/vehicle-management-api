class Eleicao(
    private val totalEleitores: Int,
    private val votosValidos: Int,
    private val votosBrancos: Int,
    private val votosNulos: Int
) {
    fun percentualValidos() = (votosValidos * 100.0) / totalEleitores
    fun percentualBrancos() = (votosBrancos * 100.0) / totalEleitores
    fun percentualNulos() = (votosNulos * 100.0) / totalEleitores
}

fun main() {
    val eleicao = Eleicao(1000, 800, 150, 50)

    println("Percentual de votos válidos: %.2f%%".format(eleicao.percentualValidos()))
    println("Percentual de votos brancos: %.2f%%".format(eleicao.percentualBrancos()))
    println("Percentual de votos nulos: %.2f%%".format(eleicao.percentualNulos()))
}
