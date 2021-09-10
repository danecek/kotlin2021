package fit.bikot.coroutineprimes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun CoroutineScope.nat(): ReceiveChannel<Int> {
    var c = 1
    return produce {
        while (true)
            send(c++);
    }
}

fun CoroutineScope.filtern(n: Int, ic: ReceiveChannel<Int>): ReceiveChannel<Int> {
    return produce {
        while (true) {
            val x = ic.receive();
            if (x % n != 0)
                send(x)
        }
    }
}

fun CoroutineScope.prime(): ReceiveChannel<Int> {
    return produce {
        val nat = nat()
        nat.receive()// 1
        send(nat.receive()) //2
        var fn = filtern(2, nat)
        while (true) {
            val e = fn.receive();
            fn = filtern(e, fn)
            send(e)
        }
    }
}

suspend fun maxPrime(timeOut: Long): Int {
    var maxp = 0
    return withTimeoutOrNull(timeOut) {
        prime().consumeEach {
            maxp = it
        } as Nothing
    } ?: maxp

}

fun main() {
    runBlocking {
        prime().take(20).consumeEach { println(it) }
        println(maxPrime(3000))
    }
}

