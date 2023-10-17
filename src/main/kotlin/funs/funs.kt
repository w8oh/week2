package funs

import interfaces.IO
import java.util.regex.Matcher
import java.util.regex.Pattern

 fun vitaskivatel(anyIO: IO) {

    var expression = anyIO.read()

    while (expression != "stop") {

        val regex = """^\d+\s*[+-/*]\s*\d+${'$'}""".toRegex()

        if (regex.matches(expression)) {
            val x1 = exprFromString(Pattern.compile("^\\d+"), expression).toInt()
            val x2 = exprFromString(Pattern.compile("\\d+$"), expression).toInt()
            val op = exprFromString(Pattern.compile("[+-/*]"), expression)
            val result = calculateResult(op, x1, x2)
            anyIO.write(result.bin())
        } else {
            anyIO.write("Неверный формат выражения")
        }

        expression = anyIO.read()
    }
}

 fun calculateResult(op: String, x1: Int, x2: Int): Int {

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

fun exprFromString(pattern: Pattern, str: String): String {
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