package bikot

import java.util.*
import kotlin.system.measureNanoTime

fun <T> q(t: @UnsafeVariance T) {}

fun <T> Sequence<T>.eagerFilter(predicate: (T) -> Boolean): Sequence<T> {
    val al = mutableListOf<T>()
    this.forEach { if (predicate(it)) al.add(it) }
    return object : Sequence<T> {
        override fun iterator(): Iterator<T> {
            return al.iterator()
        }
    }
}

fun <T, R> Sequence<T>.eagerMap(transform: (T) -> R): Sequence<R> {
    val al = mutableListOf<R>()
    this.forEach { al.add(transform(it)) }
    return object : Sequence<R> {
        override fun iterator(): Iterator<R> {
            return al.iterator()
        }
    }
}

fun <T, R> Sequence<T>.lazyMap(transform: (T) -> R): Sequence<R> {
    val recIt: Iterator<T> = this.iterator()
    return object : Sequence<R> {
        override fun iterator(): Iterator<R> {
            return object : Iterator<R> {
                override fun hasNext(): Boolean {
                    return recIt.hasNext()
                }

                override fun next(): R {
                    return transform(recIt.next())
                }
            }
        }
    }
}

fun <T> Sequence<T>.lazyFilter(predicate: (T) -> Boolean): Sequence<T> {

    return object : Sequence<T> {

        val recIt: Iterator<T> = this@lazyFilter.iterator()

        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {
                var nxt: T? = null

                override fun hasNext(): Boolean {
                    while (recIt.hasNext()) {
                        val n = recIt.next()
                        if (predicate(n)) {
                            nxt = n
                            return true
                        }
                    }
                    nxt = null
                    return false
                }

                override fun next(): T {
                    return nxt ?: throw NoSuchElementException()
                }
            }
        }
    }
}

fun main() {
    val l = List<Int>(10_000_000) { it }
    val ls: Sequence<Int> = l.asSequence()
    println(
        "lazy=${
            measureNanoTime {
                ls.lazyMap { it * it }.lazyFilter { it > 1000000 }
            } / 1000000
        }ms")
    val es = l.asSequence()
    println(
        "eager=${
            measureNanoTime {
                es.eagerMap { it * it }.eagerFilter { it > 1000000 }
            } / 1000000
        }ms")


}

