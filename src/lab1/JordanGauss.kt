package lab1

import kotlin.math.abs
import kotlin.math.round

@Suppress("SameParameterValue", "NAME_SHADOWING")
class JordanGauss {

    private val eps = 1E-10
    private val n = 5
    private val m = 5
    private val arrayOfArrays = arrayOf(
        arrayOf(-2.0, -5.0, -1.0, 9.0, 3.0, -66.0),
        arrayOf(1.0, -5.0, -6.0, 10.0, -1.0, -98.0),
        arrayOf(-2.0, 2.0, -2.0, 9.0, 1.0, -103.0),
        arrayOf(6.0, -1.0, 3.0, 10.0, 7.0, -18.0),
        arrayOf(-4.0, 4.0, 5.0, -3.0, 1.0, 24.0)
    )
    private val answer = arrayOfNulls<Double>(n)
    private val mark = arrayOfNulls<Boolean>(m)
    private val maxValue = m.coerceAtMost(n)

    fun main() {
        findMaxValueInColumn(maxValue, n, m, arrayOfArrays)
        changeFinalMatrix(maxValue, n, arrayOfArrays)
        setMarkInLine(m, n, mark, arrayOfArrays)
        setZeroes(m, n, mark, arrayOfArrays)
        swapInMatrix(m, n, mark, arrayOfArrays)
        printFinalMatrix(m, n, arrayOfArrays)
        printAnswer(m, mark, n, answer, arrayOfArrays)
    }

    private fun findMaxValueInColumn(maxValue: Int, n: Int, m: Int, arrayOfArrays: Array<Array<Double>>) {
        for (k in 0 until maxValue) {
            println("Поиск максимального элемента в столбике ${k + 1}  ")
            printMatrix(m, n, arrayOfArrays)

            var maxValue = 0.0
            var positionOfLineWithMaxV = k
            for (i in k until m) {
                if (abs(arrayOfArrays[i][k]) > maxValue) {
                    maxValue = abs(arrayOfArrays[i][k])
                    positionOfLineWithMaxV = i
                }
            }
            for (j in 0 until n + 1) {
                val tmp = arrayOfArrays[k][j]
                arrayOfArrays[k][j] = arrayOfArrays[positionOfLineWithMaxV][j]
                arrayOfArrays[positionOfLineWithMaxV][j] = tmp
            }
            if (abs(maxValue) < eps) {
                continue
            }
            changeMatrix(m, k, n, arrayOfArrays)
            println("Максимальное значение столбика ${k + 1} = ${
                String.format("%.3f",
                    maxValue)
            } в уравнении ${positionOfLineWithMaxV + 1}")
            println()
        }
    }

    private fun changeMatrix(m: Int, k: Int, n: Int, arrayOfArrays: Array<Array<Double>>) {
        for (i in 0 until m) {
            if (i == k) continue
            val multiplier = arrayOfArrays[i][k].div(arrayOfArrays[k][k])
            for (j in k until n + 1) {
                arrayOfArrays[i][j] = arrayOfArrays[i][j].minus(multiplier * arrayOfArrays[k][j])
            }
        }
    }

    private fun changeFinalMatrix(maxValue: Int, n: Int, arrayOfArrays: Array<Array<Double>>) {
        for (k in 0 until maxValue) {
            if (abs(arrayOfArrays[k][k]) > eps) {
                val multiplier = arrayOfArrays[k][k]
                if (abs(multiplier) < eps) continue
                for (j in k until n + 1) {
                    arrayOfArrays[k][j] = arrayOfArrays[k][j].div(multiplier)
                }
            }
        }
    }

    private fun setMarkInLine(m: Int, n: Int, mark: Array<Boolean?>, arrayOfArrays: Array<Array<Double>>) {
        for (i in 0 until m) {
            mark[i] = false
        }
        for (k1 in 0 until m) {
            if (mark[k1] == true) continue
            for (k2 in k1 + 1 until m) {
                var isEqual = true
                for (j in 0 until n + 1) {
                    if (abs(arrayOfArrays[k1][j] - arrayOfArrays[k2][j]) > eps) {
                        isEqual = false
                        break
                    }
                }
                if (isEqual) {
                    mark[k2] = true
                }
            }
        }
    }

    private fun setZeroes(m: Int, n: Int, mark: Array<Boolean?>, arrayOfArrays: Array<Array<Double>>) {
        for (i in 0 until m) {
            var countOfZeroes = 0
            for (j in 0 until n + 1) {
                if (abs(arrayOfArrays[i][j]) < eps) {
                    countOfZeroes++
                    arrayOfArrays[i][j] = 0.0
                }
            }
            if (countOfZeroes == n + 1) {
                mark[i] = true
            }
            if (countOfZeroes == n && abs(arrayOfArrays[i][n]) > eps) {
                println("The system of equations is inconsistent")
                return
            }
        }
    }

    private fun swapInMatrix(m: Int, n: Int, mark: Array<Boolean?>, arrayOfArrays: Array<Array<Double>>) {
        for (i in 0 until m) {
            for (j in i + 1 until m) {
                if (mark[i] == true && mark[j] == false) {
                    swapLines(i, j, n, arrayOfArrays, mark)
                }
            }
        }
    }

    private fun swapLines(k1: Int, k2: Int, n: Int, arrayOfArrays: Array<Array<Double>>, mark: Array<Boolean?>) {
        for (j in 0 until n) {
            val tmp: Double = arrayOfArrays[k1][j]
            arrayOfArrays[k1][j] = arrayOfArrays[k2][j]
            arrayOfArrays[k2][j] = tmp
        }
        val tmp: Boolean? = mark[k1]
        mark[k1] = mark[k2]
        mark[k2] = tmp
    }

    private fun printMatrix(m: Int, n: Int, arrayOfArrays: Array<Array<Double>>) {
        for (i in 0 until m) {
            for (j in 0 until n + 1) {
                print(String.format("%.3f", arrayOfArrays[i][j]) + " ")
            }
            println()
        }
    }

    private fun printFinalMatrix(m: Int, n: Int, arrayOfArrays: Array<Array<Double>>) {
        for (i in 0 until m) {
            for (j in 0 until n + 1) {
                print(round(arrayOfArrays[i][j]).toString() + " ")
            }
            println()
        }
    }

    private fun printAnswer(
        m: Int,
        mark: Array<Boolean?>,
        n: Int,
        answer: Array<Double?>,
        arrayOfArrays: Array<Array<Double>>,
    ) {
        var countOfMarks = 0
        for (i in 0 until m) {
            if (mark[i] == true) countOfMarks++
        }
        val bottomBorder = m - 1 - countOfMarks
        if (bottomBorder == n - 1) {
            for (k in n - 1 downTo 0) {
                answer[k] = arrayOfArrays[k][n] / arrayOfArrays[k][k]
            }

            println("Ответ:")
            for (k in 0 until n - 1) {

                print(answer[k]?.let { round(it).toString() } + " ")
            }
            println(answer[n - 1]?.let { round(it).toString() })
        } else {
            var cntOfFreeVariables = n - (bottomBorder + 1)
            val markedVariables = arrayOfNulls<Boolean>(n)
            for (i in 0 until n) {
                markedVariables[i] = false
            }
            for (j in 0 until n) {
                var cntOfZeroes = 0
                for (i in 0 until bottomBorder) {
                    if (abs(arrayOfArrays[i][j]) < eps) {
                        cntOfZeroes++
                    }
                }
                if (cntOfZeroes == bottomBorder + 1) {
                    if (cntOfFreeVariables > 0) {
                        markedVariables[j] = true
                        cntOfFreeVariables--
                    }
                }
            }
            for (i in n - 1 downTo 0) {
                if (cntOfFreeVariables == 0) break
                markedVariables[i] = true
                cntOfFreeVariables--
            }
            println("Initialization of free variables:")
            for (i in 0 until n) {
                if (markedVariables[i] == true) {
                    answer[i] = 1.0
                    println("Let: $i-th variable assigned: 1.0")
                }
            }
            println("Answer:")
            for (i in 0 until n) {
                if (markedVariables[i] == true) {
                    println("$i-th variable is free")
                }
            }
            for (i in bottomBorder downTo 0) {
                var curSum = 0.0
                var curVariable = 0
                for (j in 0 until n) {
                    if (markedVariables[j] == false && abs(arrayOfArrays[i][j]) > eps) {
                        curVariable = j
                        break
                    }
                }
                print("X[$curVariable] = ")
                for (j in 0 until n) {
                    if (markedVariables[j] == true) {
                        curSum += answer[j]!! * arrayOfArrays[i][j]
                        print("(" + -arrayOfArrays[i][j] + "/" + arrayOfArrays[i][curVariable] + ")" + "*X[" + j + "] ")
                    }
                }
                println()
                curSum *= -1.0
                curSum += arrayOfArrays[i][n]
                for (j in 0 until n) {
                    if (markedVariables[j] == false && abs(arrayOfArrays[i][j]) > eps) {
                        answer[j] = curSum / arrayOfArrays[i][j]
                        markedVariables[j] = true
                        break
                    }
                }
            }
            println("One of the solutions:")
            for (k in 0 until n - 1) {
                print(answer[k].toString() + " ")
            }
            println(answer[n - 1])
        }
    }
}