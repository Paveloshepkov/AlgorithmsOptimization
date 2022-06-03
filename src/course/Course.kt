package course

@Suppress("SameParameterValue")
class Course {

    internal class OutObject<T> {
        var outArgValue: T? = null
    }

    private val n = 3
    private val m = 6
    private val matrix = arrayOf(
        floatArrayOf(2f, 1f, -1f, 0f, 0f, 10f),
        floatArrayOf(3f, 4f, 0f, -1f, 0f, 30f),
        floatArrayOf(3f, 8f, 0f, 0f, -1f, 42f),
        floatArrayOf(10f, 3f, 0f, 0f, 0f, 0f)
    )

    fun main() {
        println("Start Matrix")
        printMatrix(matrix, n, m)
        println("Start Gauss Method")
        gaussMethod(matrix, n, m)
        println("Solution Gauss Method's ")
        printAnswer(matrix, n, m)
        simplex(matrix, n, m)
        printAnswer(matrix, n, m)
        println("F_min = " + -matrix[n][m - 1])
    }

    private fun printMatrix(matrix: Array<FloatArray>, n: Int, m: Int) {
        println("  X1    X2    X3    X4    X5    B")
        for (i in 0 until n) {
            for (j in 0 until m) {
                print(String.format("%.3f", matrix[i][j]) + " ")
            }
            println()
        }
        println()
    }

    private fun gaussMethod(matrix: Array<FloatArray>, n: Int, m: Int) {
        for (i in 0 until n) {
            calc(matrix, n + 1, m, i, i)
            printMatrix(matrix, n + 1, m)
        }
    }

    private fun calc(matrix: Array<FloatArray>, n: Int, m: Int, ik: Int, jk: Int) {
        var z = matrix[ik][jk]
        for (i in 0 until m) {
            matrix[ik][i] /= z
        }
        for (i in 0 until n) {
            if (i != ik) {
                z = matrix[i][jk]
                for (j in 0 until m) {
                    val p = matrix[ik][j] * z
                    matrix[i][j] -= p
                }
            }
        }
    }

    private fun check(matrix: Array<FloatArray>, n: Int, m: Int): Boolean {
        var minus = 0
        for (i in 0 until m - 1) {
            if (matrix[n][i] < 0) {
                minus++
            }
        }
        return minus > 0
    }

    private fun findMainElement(
        matrix: Array<FloatArray>,
        n: Int,
        m: Int,
        ik: OutObject<Int>,
        jk: OutObject<Int>
    ) {
        var mainEl = matrix[n][0]
        jk.outArgValue = 0
        for (i in 0 until m - 1) {
            if (mainEl > matrix[n][i] && matrix[n][i] < 0) {
                mainEl = matrix[n][i]
                jk.outArgValue = i
            }
        }
        val array = FloatArray(n)
        for (i in 0 until n) {
            array[i] = matrix[i][m - 1] / matrix[i][jk.outArgValue!!]
        }
        mainEl = array[0]
        ik.outArgValue = 0
        for (i in 0 until n) {
            if (mainEl > array[i] && array[i] > 0) {
                mainEl = array[i]
                ik.outArgValue = i
            }
        }
    }

    private fun simplex(matrix: Array<FloatArray>, n: Int, m: Int) {
        println("Simplex matrix : ")
        printMatrix(matrix, n + 1, m)
        var ik: Int
        var jk: Int
        while (check(matrix, n, m)) {
            val temptIk: OutObject<Int> = OutObject()
            val temptJk: OutObject<Int> = OutObject()
            findMainElement(matrix, n, m, temptIk, temptJk)
            jk = temptJk.outArgValue!!
            ik = temptIk.outArgValue!!
            println("Find element in string ${ik + 1} and column ${jk + 1}")
            calc(matrix, n + 1, m, ik, jk)
            println("\n Simplex matrix after calculate")
            printMatrix(matrix, n + 1, m)
        }
    }

    private fun printAnswer(matrix: Array<FloatArray>, n: Int, m: Int) {
        for (i in 0 until n) {
            for (j in 0 until m - 1) {
                if (matrix[i][j] == 1f) {
                    println("X" + (j + 1) + " = " + matrix[i][m - 1])
                }
            }
        }
        println()
    }
}