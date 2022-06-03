package lab3

import kotlin.math.abs
import kotlin.math.pow

class ArrowHurwitz(
    private var x1_0: Double,
    private var x2_0: Double,
    private var lambda: Double,
    private var delta: Double,
) {

    private fun f(x1: Double, x2: Double): Double {
        return -(x1 - 1.0).pow(2.0) - 5.0 * x2
    }

    private fun dx1(x1: Double): Double {
        return -(2 * x1) + 2
    }

    private fun dx2(): Double {
        return -5.0
    }

    private fun g(x1: Double, x2: Double): Double {
        return 4 - (x1 - 5).pow(2.0) - (x2 - 5).pow(2.0)
    }

    private fun gdx1(x1: Double): Double {
        return -2 * x1 + 10
    }

    private fun gdx2(x2: Double): Double {
        return -2 * x2 + 10
    }

    fun main() {
        var x1 = x1_0
        var x2 = x2_0
        var alpha = 0.0
        var dlt = Double.MAX_VALUE
        var prefix1 = x1_0
        var prefix2 = x2_0
        var i = 0
        println("+------------------------------------------------------+ ")
        println("|Итерации|  x1   |   x2  | f(x)    | delta   |   g     | ")
        println("+------------------------------------------------------+ ")
        while (dlt > delta) {

            alpha = if (g(x1, x2) < 0) alpha - lambda * g(x1, x2) else 0.0

            x1 += lambda * (dx1(x1) + alpha * gdx1(x1))
            x2 += lambda * (dx2() + alpha * gdx2(x2))

            if (x1 > 0 && x2 > 0 && g(x1, x2) > 0) {
                dlt = abs(f(x1, x2) - f(prefix1, prefix2))
                prefix1 = x1
                prefix2 = x2
            }
            if (i % 1000 == 0)
                println(
                    "|   " + String.format("%4d |", i) +
                            "" + String.format(" %.3f |", x1) +
                            "" + String.format(" %.3f |", x2) +
                            "" + String.format(" %.3f |", f(x1, x2)) +
                            "" + String.format(" %.5f |", dlt) +
                            "" + String.format(" %.3f  |", g(x1, x2)))
            i++
        }
        println("+------------------------------------------------------+ ")
        println(
            "|   " + String.format("%4d |", i) +
                    "" + String.format(" %.3f |", x1) +
                    "" + String.format(" %.3f |", x2) +
                    "" + String.format(" %.3f |", f(x1, x2)) +
                    "" + String.format(" %.5f |", dlt) +
                    "" + String.format("  %.3f  |", g(x1, x2)))
        println("+------------------------------------------------------+ ")
    }
}