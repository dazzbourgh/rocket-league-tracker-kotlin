package zhi.yest.rocketleaguetracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.domain.OfferItem
import zhi.yest.rocketleaguetracker.misc.Fetcher

@Component
class OfferService
@Autowired constructor(@Qualifier("retryingFetcher") private val fetcher: Fetcher,
                       private val priceService: PriceService,
                       @Value("\${trade.page.url}") private val tradePageUrl: String) {

    fun getOffers(filter: Filter, pagesAmount: Int = 10): List<Offer> {
        return fetcher.fetchPage("$tradePageUrl?$filter")
                .let { offerContainerRegex.findAll(it) }
                .map { it.value }.toList()
                .flatMap {
                    val hasItems = getItems(it, hasRegex)
                    val wantsItems = getItems(it, wantsRegex)
                    if (hasItems.size == wantsItems.size)
                        return@flatMap hasItems.zip(wantsItems)
                                .map { Offer(it.first, it.second) }
                    else return@flatMap listOf<Offer>()
                }
    }

    private fun getItems(string: String, regex: Regex): List<OfferItem> {
        return regex.findAll(string)
                .map { it.value }
                .map { offerItemRegex.find(it) }
                .filterNotNull()
                .map { toOfferItem(it) }
                .toList()
    }

    private fun toOfferItem(matchingResult: MatchResult): OfferItem {
        val destructured = matchingResult.destructured
        val name = destructured.component2()
        val quantity = destructured.component3().toInt()
        val price = priceService.getPrice(name)
        val totalPrice = price * quantity
        return OfferItem(destructured.component1().toInt(),
                name,
                quantity,
                price,
                totalPrice,
                null,
                null)
    }
}

private val hasRegex: Regex = """<\.>""".toRegex()
private val wantsRegex: Regex = """<\.>""".toRegex()
private val offerContainerRegex: Regex = """""".toRegex()
private val offerItemRegex: Regex = """""".toRegex()