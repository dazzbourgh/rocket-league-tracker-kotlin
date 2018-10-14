package zhi.yest.rocketleaguetracker.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.not
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.misc.Fetcher
import java.io.File

object OfferServiceSpec : Spek({
    given("an offer service") {
        val url = "https://trade/url"
        val page = File("./target/test-classes/offer-service-input.html").readText()
        val fetcher: Fetcher = mock {
            on { fetchPage(any()) } doReturn page
        }
        val priceService: PriceService = mock {
            on { getPrice(any()) } doReturn 1f
        }
        val sut = OfferService(fetcher, priceService, url)
        on("getting offers") {
            val offers: List<Offer> = sut.getOffers(Filter(1, 0, 0, 0), 1)
            it("should return offer list") {
                assertEquals(true, false)
                assertThat(offers, not(empty()))
                assertEquals("Bubbly (Black Market)", offers[0].has.name)
                assertEquals("Apex", offers[1].has.name)
                assertEquals("Sovereign A/T", offers[2].has.name)
                assertEquals("Key", offers[0].wants.name)
                assertEquals("Key", offers[1].wants.name)
                assertEquals("Key", offers[2].wants.name)
            }
        }
    }
})
