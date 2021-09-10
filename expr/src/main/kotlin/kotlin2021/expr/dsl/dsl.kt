package kotlin2021.expr.dsl

import kotlin2021.expr.Bin
import kotlin2021.expr.Expr
import kotlin2021.expr.Num
import kotlin2021.expr.Op
import java.util.*

/*
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
}*/

fun Queue<Expr>.pls(operands: Queue<Expr>.() -> Unit): Expr {
    val bin = ArrayDeque<Expr>()
        .apply { operands() }
        .apply { check(size == 2) }
        .run {
            Bin(Op.PLS, remove(), remove())
        }
    return apply { add(bin) }.last()
}

fun Queue<Expr>.mlt(operands: Queue<Expr>.() -> Unit): Expr {
    val bin = ArrayDeque<Expr>()
        .apply { operands() }
        .apply { check(size == 2) }
        .run {
            Bin(Op.MLT, remove(), remove())
        }
    return apply { add(bin) }.last()
}

fun Queue<Expr>.num(value: Int): Expr {
    return apply { add(Num(value)) }.last()
}

fun main() {
    val e = ArrayDeque<Expr>().mlt {
        num(3)
        pls {
            num(4)
            num(5)
        }

    }
    println(e)
}

