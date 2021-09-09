package kotlin2021.expr.patt

import kotlin2021.expr.Bin
import kotlin2021.expr.Expr
import kotlin2021.expr.Num
import kotlin2021.expr.*

interface Patt {
    fun match(e : Expr) : Boolean
}

object UnivPatt: Patt {
    override fun match(e: Expr): Boolean = true
}

open class NumPatt(val value: Int) : Patt {
    override fun match(e: Expr) = e is Num && e.value == value
}
object OnePatt : NumPatt(1)
object ZeroPatt : NumPatt(0)

object OnePlsPatt

fun Expr.optim() =
    when {

      else -> this
    }


fun main() {
    val e = Bin(Op.PLS, Zero, One)
    println(e.optim()) //     println(UnivPatt.i(One).match())
}