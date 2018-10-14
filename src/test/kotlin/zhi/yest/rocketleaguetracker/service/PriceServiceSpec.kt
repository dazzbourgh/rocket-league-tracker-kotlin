package zhi.yest.rocketleaguetracker.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import zhi.yest.rocketleaguetracker.misc.DomFetcher

object PriceServiceSpec : Spek({
    given("a price service") {
        val mockDomFetcher = mock<DomFetcher> {
            on { fetchPage(any()) } doReturn Jsoup.parse(MOCK_PAGE)
        }
        val priceService = PriceService(mockDomFetcher, "random/url")
        on("initializing and getting Zephyr Crate price") {
            priceService.init()
            val price = priceService.getPrice("Zephyr Crate")
            it("returns price of 0.29") {
                assertEquals(0.29f, price, 0.001f)
            }
        }
    }
})

val MOCK_PAGE = """
    <!DOCTYPE HTML>
<html lang="en" class="page">

<head>
</head>

<body>
    <div class="price_cat active">
        <div class="headline">Crates</div>
        <div class="row">
            <div class="col-xl-1 col-lg-2 col-sm-3 col-xs-4 col-price active" data-name="zephyr crate">
                <div class="price_single" style="background: #555;">
                    <h4><a href="/prices/1661">Zephyr Crate</a></h4>
                    <div class="cock_and_img">
                        <div class="cock">
                            <i class='fa fa-clock-o green' data-html='true' data-trigger='hover' data-toggle='popover' data-placement='top' title='Calculation Method' data-content='Prices are calculated over a <span class="green">very short time span</span>.'></i>
                        </div>
                        <div class="chart">
                            <a href="/prices/1661"><img src="https://chart.apis.google.com/chart?chco=e66f33&amp;chf=bg,s,00000000&amp;chd=s:vvvttttttllllllaaaZZZaaaZZZSSS&amp;cht=ls&amp;chs=40x20&amp;chxr=0,0.73,0.949"></a>
                        </div>
                        <img src="/assets/rl-items/1661/0-61ccebb7cd65ebdfc4c68a0a1611636ee8a1cf0872fcc3878815ec4625323391.png" style="height:auto;width:100%;">
                    </div>
                    <div class="footer" data-id="1661" data-cert="" data-color="">
                        0.29 <i class="fa fa-key"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
""".trimIndent()