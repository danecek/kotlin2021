package kotlin2021.textelem

abstract class TextElem {
    abstract val content: Array<String>
    val width: Int
        get() = content[0].length
    val height: Int
        get() = content.size


    override fun toString() = content.joinToString(separator = "\n")
    infix fun above(that: TextElem): TextElem {
        require(this.width == that.width) { -> "ruzne sirky" }
        return BasicElem(content + that.content)
    }

    infix fun beside(that: TextElem): TextElem {
        require(this.height == that.height) { -> "ruzne vysky" }
        return BasicElem(Array(height) { ind -> this.content[ind] + that.content[ind] })
    }
}

class BasicElem(_content: Array<String>) : TextElem() {
    init {
        require(!_content.isEmpty()) { "prazdny element" }
        for (l in _content)
            require(l.length == _content[0].length) { "ruzne delky radek" }
    }

    override val content: Array<String> = _content
}

//class BasicElem(override val content: Array<String>) : TextElem()

open class CharElem(c: Char, width: Int, height: Int) : TextElem() {

    override val content: Array<String> = Array(height) { c.toString().repeat(width) }

}

class EmptyElem(width: Int, height: Int) : CharElem(' ', width, height)


fun main() {
    val e1 = BasicElem(arrayOf("aaa", "bbb"))
    val e2 = BasicElem(arrayOf("xxx", "yyy", "zzz"))
    println(e1 above e2)
    // println(e1 beside e2)
    println(CharElem('*', 10, 20))
}