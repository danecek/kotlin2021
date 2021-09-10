package kotlin2021.expr.dsl

import kotlin2021.expr.Bin
import kotlin2021.expr.Expr
import kotlin2021.expr.Num
import kotlin2021.expr.Op
import java.util.*

fun Stack<Expr>.num(value: Int): Expr {
    val n = Num(value)
    push(n)
    return n
}

fun Stack<Expr>.pls(operands: Stack<Expr>.() -> Unit): Expr {
    operands()
    val r = pop()
    val l = pop()
    val b = Bin(Op.PLS, l, r)
    push(b)
    return b
}

fun Stack<Expr>.mlt(operands: Stack<Expr>.() -> Unit): Expr {
    operands()
    val r = pop()
    val l = pop()
    val b = Bin(Op.MLT, l, r)
    push(b)
    return b
}

/*fun Queue<Expr>.pls(operands: Queue<Expr>.() -> Unit): Expr {
    val r = ArrayQueue<Expr>().apply{ operands }.add( Bin(Op.PLS,  , r))
    operands()
    val r = pop()
    val l = pop()
    val b = Bin(Op.PLS, l, r)
    push(b)
    return b
}*/

fun main() {
    val e = Stack<Expr>().pls {
        num(3)
        pls{
            num(5)
            num(5)
        }

    }
    println(e)
}
