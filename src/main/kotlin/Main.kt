//для чтения с файла
import java.io.File
import java.util.*
//для вытаскивания из строки выражения
import java.util.regex.Matcher
import java.util.regex.Pattern


fun main(args: Array<String>) {

    println("\nПривет! Это КрутойВычислитель3000!\n")
    while (true) {
        println(
            "Для выбора пункта меню введите номер пункта и нажмите Enter:\n" +
                    "1. Вычисления в терминале.\n2. Вычисления в файлах.\n3. Выход."
        )

        var workType = 0

        try {

            workType = readln().toInt()

            if (workType < 1 || workType > 3) {
                println("Не существует выбранного пункта меню.")
            }

        } catch (e: NumberFormatException) {
            println("Введено некорректное число")
        }

        when (workType) {
            1 -> {
                println(
                    "Вы выбрали работу с консолью.\n" +
                            "Для вычисления выражения введите его в формате [число][знак][число] и нажмите Enter\n" +
                            "По завершению работы введите stop с новой строки и нажмите Enter :)"
                )

                val consoleIo = ConsoleIo()

                val Expression = consoleIo.read()

                Vitaskivatel(Expression, consoleIo, consoleIo)
            }

            2 -> {
                println(
                    "Вы выбрали работу с файлами.\n" +
                            "Для вычисления выражений введите их в файл input.txt в формате [число][знак][число]\n" +
                            "По завершению ввода введите stop с новой строки. Результат будет в файле output.txt."
                )

                val inputFile = FileIo("input.txt")
                val outputFile = FileIo("output.txt")

                val Expression = inputFile.read()

                Vitaskivatel(Expression, outputFile, inputFile)
            }

            3 -> {
                break
            }
        }
    }
}

private fun Vitaskivatel(Expression: String, outputFile: IO, inputFile: IO) {

    var Expression1 = Expression

    while (Expression1 != "stop") {

        val regex = """^\d+\s*[+-/*]\s*\d+${'$'}""".toRegex()

        if (regex.matches(Expression1)) {
            val x1 = ExprFromString(Pattern.compile("^\\d+"), Expression1).toInt()
            val x2 = ExprFromString(Pattern.compile("\\d+$"), Expression1).toInt()
            val op = ExprFromString(Pattern.compile("[+-/*]"), Expression1)
            val result = CalculateResult(op, x1, x2)
            outputFile.write(result.bin())
        } else {
            outputFile.write("Неверный формат выражения")
        }

        Expression1 = inputFile.read()
    }
}

private fun CalculateResult(op: String, x1: Int, x2: Int): Int {

    when (op) {
        "+" -> {
            return action(x1, x2, ::sum)
        }

        "-" -> {
            return action(x1, x2, ::subtract)
        }

        "*" -> {
            return action(x1, x2, ::multiply)
        }

        "/" -> {
            return action(x1, x2, ::division)
        }
        else -> {
            return 0
        }
    }
}

fun Int.bin(): String {
    return Integer.toBinaryString(this)
}

fun ExprFromString(pattern: Pattern, str: String): String {
    val matcher: Matcher = pattern.matcher(str)
    matcher.find()
    return matcher.group()
}


fun action(n1: Int, n2: Int, op: (Int, Int) -> Int): Int {
    return op(n1, n2)
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun subtract(a: Int, b: Int): Int {
    return a - b
}

fun multiply(a: Int, b: Int): Int {
    return a * b
}

fun division(a: Int, b: Int): Int {
    return a / b
}

interface IO {
    fun read(): String
    fun write(currentExpression: String)
}

class ConsoleIo : IO {

    override fun read(): String {
        return readln()
    }

    override fun write(currentExpression: String) {
        println(currentExpression)
    }

}

class FileIo(_path: String) : IO {

    val path: String
    var sc: Scanner

    init {
        path = _path
        sc = Scanner(File(path))
    }

    override fun read(): String {
        return sc.nextLine()
    }

    override fun write(currentExpression: String) {
        File(path).appendText(currentExpression + "\n")
    }

}
