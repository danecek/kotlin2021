package fit.bikot.rational

class Rational(_n: Int, _d: Int) {
    val n: Int
    val d: Int

    constructor(_n: Int) : this(_n, 1)

    init {
        check(_d != 0)
        val gc = gcd(_n, _d)
        n = _n / gc
        d = _d / gc
    }

    operator fun times(that: Rational) = Rational(n * that.n, d * that.d)
    operator fun times(that: Int) = Rational(n * that, d)
    operator fun div(that: Rational) = this * that.inv()
    operator fun div(that: Int) = Rational(n, d * that)
    fun inv() = Rational(d, n)

    override fun equals(other: Any?): Boolean {
        return this === other ||
                other is Rational
                // && other::class == Rational::class
                && n == other.n
                && d == other.d
    }

    companion object {
        val ZERO = Rational(0)
        fun gcd(n: Int, d: Int): Int =
            if (d == 0) n
            else gcd(d, n % d)
    }

    override fun toString() = "$n/$d"
    override fun hashCode(): Int {
        var result = n
        result = 31 * result + d
        return result
    }
}

operator fun Int.times(that: Rational) = Rational(this * that.n, that.d)
operator fun Int.not() = Rational(this)

fun main() {
    val r1 = Rational(2, 3)
    val r2 = Rational(4, 6)
    println(r1 == r2)
    println(r1 * r2)
    println(r1 * 2)
    println(2 * r1)
    println(!1 / 3)
}