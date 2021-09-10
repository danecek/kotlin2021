package kotlin2021.expr.dsl

import kotlin2021.expr.*

operator fun Expr.plus(r : Expr) = Bin(Op.PLS, this, r)
operator fun Expr.plus(r : Int) = Bin(Op.PLS, this, Num(r))

operator fun Int.not() = Num(this)

fun main() {
    println(!1 + 2)

}