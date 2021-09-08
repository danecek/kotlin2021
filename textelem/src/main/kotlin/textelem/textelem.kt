package textelem

abstract class TextElem {
    abstract val content: Array<String>
    val width: Int
        get() = content[0].length
    val height: Int
        get() = content.size

    override fun toString() = content.joinToString(separator = "\n")
    infix fun above(that: TextElem) = BasicElem(content + that.content)
    infix fun beside(that: TextElem): TextElem {
        require(this.height == that.height) {->"ruzne vysky"}
        return BasicElem(Array(height) { ind -> this.content[ind] + that.content[ind] })
    }
}

class BasicElem(_content: Array<String>) : TextElem() {
    init {
        require(!_content.isEmpty())
        for(l in _content)
            require(l.length == _content[0].length)
    }
    override val content: Array<String> = _content
}
//class BasicElem(override val content: Array<String>) : TextElem()

fun main() {
    val e1 = BasicElem(arrayOf("aaa", "bbb"))
    val e2 = BasicElem(arrayOf("xxx", "yyy", "zzz"))
    println(e1 above e2)
    println(e1 beside e2)
}