import course.Course
import lab1.JordanGauss
import lab2.GameMatrix
import lab3.ArrowHurwitz
import java.util.*

fun main() {

    val `in` = Scanner(System.`in`)
    println(
        "Введите число: \n" +
                "1. JordanGauss \n" +
                "2. GameMatrix \n" +
                "3. ArrowHurwitz \n"+
                "4. Course \n"
    )
    while (true) {
        when (`in`.nextInt()) {
            1 -> JordanGauss().main()
            2 -> GameMatrix().main()
            3 -> ArrowHurwitz(4.149, 6.807, 0.001, 0.0001).main()
            4 -> Course().main()
            else -> return
        }
    }
}