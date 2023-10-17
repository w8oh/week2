package interfaces

interface IO {
    fun read(): String
    fun write(currentExpression: String)
}