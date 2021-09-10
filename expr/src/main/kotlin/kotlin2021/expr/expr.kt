package kotlin2021.expr

interface Expr {
    val value: Int
    val pri: Int
}

open class Num(override val value: Int) : Expr {
    override val pri = 0
    override fun toString() = "$value"
}

enum class Op(val pri: Int) {
    PLS(2) {
        override fun toString() = '+'.toString()
    },
    MLT(1) {
        override fun toString() = '*'.toString()
    }
}

data class Bin(val op: Op, val left: Expr, val right: Expr) : Expr {
    override val pri= op.pri
    override val value: Int = when (op) {
        Op.PLS -> left.value + right.value
        Op.MLT -> left.value * right.value
    }

    override fun toString(): String {
        fun String.encl() = "($this)"
   //     val l = if (pri >= left.pri)  left.toString() else left.toString().encl()
        val l = if (left.pri > pri)  left.toString().encl() else left.toString()
        val r = if (pri >= right.pri)  right.toString() else right.toString().encl()
        return "$l $op $r"
    }

}

val Expr.postfix : String
   get() = when(this) {
       is Num -> toString()
       is Bin -> "${left.postfix} ${right.postfix} $op"
       else ->error("")
   }

object One : Num(1)
object Zero : Num(0)
object Two : Num(2)

fun main() {
    val e = Bin(Op.MLT, Bin(Op.PLS, One, One), Two)
    val e2 = Bin(Op.MLT, Bin(Op.PLS, One, One), Two)
    println(e==e2)
    println("$e = ${e.value}")
    println(e.postfix)
}