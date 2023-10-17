import classes.ConsoleIo
import classes.FileIo
import funs.*

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

            if (workType !in 1..3) {
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

                vitaskivatel(consoleIo)
            }

            2 -> {
                println(
                    "Вы выбрали работу с файлами.\n" +
                            "Для вычисления выражений введите их в файл input.txt в формате [число][знак][число]\n" +
                            "По завершению ввода введите stop с новой строки. Результат будет в файле output.txt."
                )

                val fileIo = FileIo("input.txt", "output.txt")

                vitaskivatel(fileIo)
            }

            3 -> {
                break
            }
        }
    }
}



