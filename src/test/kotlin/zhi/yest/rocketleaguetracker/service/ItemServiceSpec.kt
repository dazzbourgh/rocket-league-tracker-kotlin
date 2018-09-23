package zhi.yest.rocketleaguetracker.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import zhi.yest.rocketleaguetracker.domain.Item
import zhi.yest.rocketleaguetracker.misc.Fetcher

class ItemServiceSpec : Spek({
    given("An item service") {
        val fetcher: Fetcher = mock {
            on { fetchPage(any()) } doReturn """
                random crap
                <option value="844">Chafed Cherry</option>
                <option value="331">Chainsaw</option>
                <option value="330">Chef's Hat</option>
                more !@$ random crap
            """.trimIndent()
        }
        val itemService = ItemService(fetcher, "http://random/url")
        on("initializing items list and getting similar items") {
            itemService.init()
            val expectedResult = listOf(Item(844, "Chafed Cherry"),
                    Item(331, "Chainsaw"))
            it("Produces 8") {
                assertEquals(expectedResult, itemService.getItems("cha"))
            }
        }
    }
})