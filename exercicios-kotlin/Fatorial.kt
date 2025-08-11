fun fatorial(n: Int): Long {
    return if (n == 0) 1
    else n * fatorial(n - 1)
}

fun main() {
    println("0! = ${fatorial(0)}")
    println("1! = ${fatorial(1)}")
    println("2! = ${fatorial(2)}")
    println("3! = ${fatorial(3)}")
    println("4! = ${fatorial(4)}")
    println("5! = ${fatorial(5)}")
    println("6! = ${fatorial(6)}")
}