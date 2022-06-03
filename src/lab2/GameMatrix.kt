package lab2

import kotlin.random.Random

class GameMatrix {

    private val games: Int = 1000
    private val p: Double = 13.0 / 17.0
    private val q: Double = 5.0 / 17.0

    private val arrayOfArray = arrayOf(
        arrayOf(10, 14),
        arrayOf(22, 9)
    )

    fun main() {
        var sum = 0
        var countA = 0
        var countB = 0
        println("+-------------------------------------------------------------------------------------------+")
        for (i in 0 until games) {
            val randomA = Random.nextDouble(0.0, 1.0)
            val randomB = Random.nextDouble(0.0, 1.0)
            var a: Int
            var b: Int
            if (randomA < p) {
                a = 0
                countA++
            } else a = 1
            if (randomB < q) {
                b = 1
                countB++
            } else b = 0
            val winA = arrayOfArray[a][b]
            sum += winA
            val avg: Double = sum.toDouble() / (i + 1)
            println(
                "|  ${String.format("%03d   ", i)} |  ${String.format("%.3f", randomA)} |  ${
                    String.format(
                        "%d       ",
                        a
                    )
                }| ${String.format("%.3f", randomB)} |  ${
                    String.format(
                        "%d     ",
                        b
                    )
                }  | ${String.format("%02d        ", winA)} | ${
                    String.format(
                        "%05d       ",
                        sum
                    )
                } | ${String.format("%.3f     ", avg)}  |"
            )

        }
        println("+-------------------------------------------------------------------------------------------+")
        println("|  Номер  | Сл. А  | Страт. А | Сл. B | Страт. B | Выигрыш А  | Накопл. A    | Ср. выигрыш А|")
        println("+-------------------------------------------------------------------------------------------+")
        println("p = (${countA}/1000; ${1000 - countA}/1000) , q(${countB}/1000;${1000 - countB}/1000) ")
    }
}