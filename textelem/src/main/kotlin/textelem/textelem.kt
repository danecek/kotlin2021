package textelem

abstract class TextElem {
    abstract val content: Array<String>
    override fun toString() = content.joinToString(separator = "\n")
    infix fun above(that: TextElem) = this
}

class BasicElem(_content: Array<String>) : TextElem() {
    override val content: Array<String> = _content
}

fun main() {
    val e1 = BasicElem(arrayOf("aaa", "bbb"))
    val e2 = BasicElem(arrayOf("xxx", "yyy", "zzz"))
    println(e1)
    println(e1 above e2)
}