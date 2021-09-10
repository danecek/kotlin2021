package kotlin2021.expr.patt.nullable

import kotlin2021.expr.*

interface Patt {
    fun match(e: Expr): Expr?
}

object UnivPatt : Patt {
    override fun match(e: Expr) = e
}

open class NumPatt(val value: Int) : Patt {
    override fun match(e: Expr): Num? = if (e is Num && e.value == value) e else null
}

open class BinPatt(val ops: Array<Op> = Op.values(), val lp: Patt = UnivPatt, val rp: Patt = UnivPatt) : Patt {
    override fun match(e: Expr): Bin? =
        if (e is Bin && e.op in ops && lp.match(e.left) != null && rp.match(e) != null) e
        else null
}

object UnivBin : BinPatt()

object ZeroPls : BinPatt(ops = arrayOf(Op.PLS), ZeroPatt, UnivPatt)
object PlsZero : BinPatt(ops = arrayOf(Op.PLS), UnivPatt, ZeroPatt)

object OnePatt : NumPatt(1)
object ZeroPatt : NumPatt(0)


fun Expr.optim(): Expr =
    ZeroPls.match(this)?.left?.optim()
        ?: PlsZero.match(this)?.right?.optim()
      //  ?: UnivBin.match(this)?.let { Bin(it.op, it.left, it.right) }
      //  ?: UnivBin.match(this)?.apply { Bin(op, left.optim(), right.optim()) }
        ?: UnivBin.match(this)?.apply { copy(left = left.optim(), right = right.optim()) }
        ?: this


fun main() {
    val e = Bin(Op.PLS, Zero, One)
    val e2 = Bin(Op.PLS, Zero, One)
    println(e == e2)
    println(e.optim()) //     println(UnivPatt.i(One).match())
}