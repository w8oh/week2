package classes

import interfaces.IO
import java.io.File
import java.util.*

class FileIo(val pathFrom: String, val pathTo: String): IO {

    val sc = Scanner(File(pathFrom))
    
    override fun read(): String {
        return sc.nextLine()
    }

    override fun write(currentExpression: String) {
        File(pathTo).appendText(currentExpression + "\n")
    }
}