//для чтения с файла
import java.io.File
import java.io.InputStream
import java.util.*
//для вытаскивания из строки выражения
import java.util.regex.Matcher
import java.util.regex.Pattern


fun main(args: Array<String>) {
    while (true) {
        println(
            "Привет! Это КрутойВычислитель3000!\nДля выбора пункта меню введите номер пункта и нажмите Enter:\n" +
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
                var Expression = consoleIo.read()

                while (Expression != "stop") {

                    val regex = """^\d+\s*[+-/*]\s*\d+${'$'}""".toRegex()

                    if (regex.matches(Expression)) {
                        var x1 = ExprFromString(Pattern.compile("^\\d+"), Expression).toInt()
                        var x2 = ExprFromString(Pattern.compile("\\d+$"), Expression).toInt()
                        var op = ExprFromString(Pattern.compile("[+-/*]"), Expression)

                        var result = 0

                        when (op) {
                            "+" -> {
                                result = action(x1, x2, ::sum)
                            }

                            "-" -> {
                                result = action(x1, x2, ::subtract)
                            }

                            "*" -> {
                                result = action(x1, x2, ::multiply)
                            }

                            "/" -> {
                                result = action(x1, x2, ::division)
                            }
                        }

                        consoleIo.write(result.bin())
                    } else {
                        consoleIo.write("Неверный формат выражения")
                    }

                    Expression = consoleIo.read()
                }

            }

            2 -> {
                println(
                    "Вы выбрали работу с файлами.\n" +
                            "Для вычисления выражений введите их в файл input.txt в формате [число][знак][число]\n" +
                            "По завершению ввода введите stop с новой строки. Результат будет в файле output.txt."
                )

                val inputFile = FileIo("input.txt")
                val outputFile = FileIo("output.txt")

                var Expression = inputFile.read()

                while (Expression != "stop") {

                    val regex = """^\d+\s*[+-/*]\s*\d+${'$'}""".toRegex()

                    if (regex.matches(Expression)) {
                        var x1 = ExprFromString(Pattern.compile("^\\d+"), Expression).toInt()
                        var x2 = ExprFromString(Pattern.compile("\\d+$"), Expression).toInt()
                        var op = ExprFromString(Pattern.compile("[+-/*]"), Expression)

                        var result = 0

                        when (op) {
                            "+" -> {
                                result = action(x1, x2, ::sum)
                            }

                            "-" -> {
                                result = action(x1, x2, ::subtract)
                            }

                            "*" -> {
                                result = action(x1, x2, ::multiply)
                            }

                            "/" -> {
                                result = action(x1, x2, ::division)
                            }
                        }
                        outputFile.write(result.bin())
                    } else {
                        outputFile.write("Неверный формат выражения")
                    }

                    Expression = inputFile.read()
                }

            }

            3 -> {
                break
            }
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
