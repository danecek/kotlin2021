package kotlin2021


class MyList(limit: Int = 1000) : java.util.List<String> {

    private val data = Array<String>(limit) {
            ind -> ""
    }
    private var _size = 0

    override fun contains(element: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(index: Int, c: MutableCollection<out String>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun listIterator(): MutableListIterator<String> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): MutableListIterator<String> {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(element: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(index: Int, element: String?) {
        require(element != null)
        data[_size++] = element
    }

    override fun iterator(): MutableIterator<String> {
        TODO("Not yet implemented")
    }

    override fun get(index: Int) = data[index]

    override fun toArray(): Array<Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> toArray(a: Array<out T>?): Array<T> {
        TODO("Not yet implemented")
    }

    override fun indexOf(o: Any?): Int {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(o: Any?): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty() = size == 0

    override fun remove(index: Int): String {
        TODO("Not yet implemented")
    }

    override fun remove(element: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun set(index: Int, element: String?): String {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<String> {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = _size

    override fun toString(): String= data.take(size).joinToString(limit = 10)

}

fun main(args: Array<String>) {
    println(MyList().isEmpty())
}

