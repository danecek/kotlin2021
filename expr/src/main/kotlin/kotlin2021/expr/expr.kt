package kotlin2021.expr

interface Expr {
    val value: Int
}

open class Num(override val value: Int) : Expr {
    override fun toString() = "$value"
}

enum class Op {
    PLS {
        override fun toString() = '+'.toString()
    },
    MLT {
        override fun toString() = '*'.toString()
    }
}

class Bin(val op: Op, val left: Expr, val right: Expr) : Expr {
    override val value: Int
        get() = when (op) {
            Op.PLS -> left.value + right.value
            Op.MLT -> left.value * right.value
        }

    override fun toString(): String {
        return "$left $op $right"
    }

}


object One : Num(1)
object Zero : Num(0)
object Two : Num(2)

fun main() {
    val e = Bin(Op.MLT, Bin(Op.PLS, One, One), Two)
    println("$e = ${e.value}")
}