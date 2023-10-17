package classes

import interfaces.IO

class ConsoleIo: IO {
    override fun read(): String {
        return readln()
    }

    override fun write(currentExpression: String) {
        println(currentExpression)
    }
}