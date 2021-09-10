package kotlin2021.expr.dsl

import kotlin2021.expr.Bin
import kotlin2021.expr.Expr
import kotlin2021.expr.Num
import kotlin2021.expr.Op

operator fun Expr.plus(r: Expr) = Bin(Op.PLS, this, r)
operator fun Expr.plus(r: Int) = Bin(Op.PLS, this, Num(r))
operator fun Expr.times(r: Expr) = Bin(Op.MLT, this, r)
operator fun Expr.times(r: Int) = Bin(Op.MLT, this, Num(r))

operator fun Int.not() = Num(this)

fun main() {
    println((!1 + 2) * 3)

}