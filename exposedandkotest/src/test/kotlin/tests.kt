import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlin2021.exposed.CustomerExposed
import kotlin2021.exposed.CustomerExposed.createRcpt
import kotlin2021.exposed.CustomerExposed.custById

fun nameMatcher() = object : Matcher<String> {
    override fun test(value: String): MatcherResult {
        return MatcherResult(value.isNotBlank() && value[0].isUpperCase(),
            "should start by capital", "should not start by capital")
    }
}

fun String.shouldBeName() = this should nameMatcher()


class PropertyExample : StringSpec({


    val tomId = CustomerExposed.createCust("Tom")
    val rcptDscs = listOf("tomsreceipt1", "tomsreceipt2")
    "cust test" {
        custById(tomId).name shouldBe "Tom"
    }
    "receipt test" {
        createRcpt(tomId, rcptDscs[0])
        createRcpt(tomId, rcptDscs[1])
        custById(tomId).rcpts.map { it.dsc } shouldContainAll rcptDscs
    }
})