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

open  class BinPatt(val ops: Array<Op> = Op.values(), val lp:Patt = UnivPatt, val rp:UnivPatt) : Patt {
    override fun match(e: Expr): Boolean = e is Bin && e.op in ops && lp.match(e.left) && rp.match(e)
}

object ZeroPls : BinPatt(ops= arrayOf(Op.PLS), ZeroPatt, UnivPatt)

object OnePatt : NumPatt(1)
object ZeroPatt : NumPatt(0)


fun Expr.optim(): Expr =
    when {
      ZeroPls.match(this) -> (this as Bin).right.optim()
      else -> this
    }


fun main() {
    val e = Bin(Op.PLS, Zero, One)
    println(e.optim()) //     println(UnivPatt.i(One).match())
}