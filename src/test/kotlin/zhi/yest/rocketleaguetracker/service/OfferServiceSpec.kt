package zhi.yest.rocketleaguetracker.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.not
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertThat
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.misc.Fetcher

class OfferServiceSpec : Spek({
    given("an offer service") {
        TODO("Implement test")
        val url = "https://trade/url"
        val page = """"""
        val fetcher: Fetcher = mock {
            on { fetchPage(any()) } doReturn page
        }
        val priceService: PriceService = mock {
            on { getPrice(eq("")) } doReturn 1f
        }
        val sut = OfferService(fetcher, priceService, url)
        on("getting offers") {
            val offers: List<Offer> = sut.getOffers(Filter(1, 0, 0, 0), 1)
            it("should return offer list") {
                assertThat(offers, not(empty()))
            }
        }
    }
})
