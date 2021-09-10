package kotlin2021.exposed

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Customer(val id: Int, val name: String, val rcpts: List<Receipt> = listOf<Receipt>())
data class Receipt(val id: Int, val dsc: String, val custId: Int)

/*interface CustomerDAO {
    fun create(name: String): Int
    fun createRcpt(custId: Int, dsc: String): Int
    val allCusts: Collection<Customer>
    fun rcptsByCusts(custId: Int): Collection<Receipt>
    val allRcpts: Collection<Receipt>
}*/

object CustomerTable : IntIdTable() {
    val name = varchar("name", 50)
}

object ReceiptTable : IntIdTable() {
    val dsc = varchar("name", 50)
    val custId = reference("cust_id", CustomerTable)
}

object CustomerExposed {
    init {
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(CustomerTable, ReceiptTable)
        }
    }

    val allCusts: Collection<Customer>
        get() = transaction {
            val custId2rcpts: Map<Int, List<Receipt>> = allRcpts.groupBy { it.custId }
            CustomerTable.selectAll().map {
                val custId = it[CustomerTable.id].value
                Customer(custId, it[CustomerTable.name], custId2rcpts[custId] ?: mutableListOf())
            }
        }


    val allRcpts: Collection<Receipt>
        get() = transaction {
            ReceiptTable.selectAll().map {
                Receipt(it[ReceiptTable.id].value, it[ReceiptTable.dsc], it[ReceiptTable.custId].value)
            }
        }


    fun createCust(name: String): Int =
        transaction {
            CustomerTable.insertAndGetId {
                it[CustomerTable.name] = name
            }.value
        }

    fun createRcpt(custId: Int, dsc: String): Int =
        transaction {
            ReceiptTable.insertAndGetId {
                it[ReceiptTable.dsc] = dsc
                it[ReceiptTable.custId] = custId
            }.value
        }

    fun rcptsByCusts(custId: Int): List<Receipt> =
        transaction {
            ReceiptTable.select { ReceiptTable.custId eq custId }.map {
                Receipt(it[ReceiptTable.id].value, it[ReceiptTable.dsc], it[ReceiptTable.custId].value)
            }
        }

    fun custById(id: Int): Customer =
        transaction {
            CustomerTable.select { CustomerTable.id eq id }.map {
                Customer(id, it[CustomerTable.name], rcptsByCusts(id))
            }.single()
        }
}



fun main() {

    with(CustomerExposed) {
        val tomId = createCust("Tom")
        val bobId = createCust("Bob")
        println(custById(tomId))
        createRcpt(tomId, "rcpt1 for Tom")
        createRcpt(tomId, "rcpt2 for Tom")
        println(custById(tomId))
        createRcpt(bobId, "rcpt for Bob")
        println(allCusts)
        println(allRcpts)
        println(rcptsByCusts(tomId))
        println(rcptsByCusts(bobId))
    }
}