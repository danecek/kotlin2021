package kotlin2021.exposed.dao

import kotlin2021.exposed.CustomerTable
import kotlin2021.exposed.ReceiptTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction


class CustomerEnt(id: EntityID<Int>) : Entity<Int>(id) {
    companion object :
        EntityClass<Int, CustomerEnt>(CustomerTable)

    var name by CustomerTable.name
    val receipts: SizedIterable<ReceiptEnt> by ReceiptEnt referrersOn ReceiptTable.custId

    override fun toString() =
        "Customer($id, $name, ${
            receipts.joinToString(prefix = "[", postfix = "]", transform = { it.dsc })})"
}

class ReceiptEnt(id: EntityID<Int>) : Entity<Int>(id) {
    companion object :
        EntityClass<Int, ReceiptEnt>(ReceiptTable)

    var dsc by ReceiptTable.dsc
    var cust by ReceiptTable.custId
}

object CustomerDAOExposed2 {

    init {
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(CustomerTable, ReceiptTable)
        }
    }

    fun createCust(_name: String): Int =
        transaction {
            CustomerEnt.new {
                name = _name
            }.id.value
        }

    fun createRcpt(custId: Int, _dsc: String): Int =
        transaction {
            ReceiptEnt.new {
                cust = CustomerEnt.findById(custId)?.id!!
                dsc = _dsc
            }.id.value
        }

    val allCusts: SizedIterable<CustomerEnt>
        get() = transaction {
            CustomerEnt.all()
        }

}

fun main() {

    with(CustomerDAOExposed2) {
        val tomId = createCust("Tom")
        createRcpt(tomId, "rcpt1 for Tom")
        createRcpt(tomId, "rcpt2 for Tom")
        transaction {
            println(allCusts.forEach { println(it) })
        }

    }
}