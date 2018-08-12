package zhi.yest.rocketleaguetracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.misc.Fetcher

@Component
class OfferService(@Autowired @Qualifier("retryingFetcher") private val fetcher: Fetcher,
                   @Autowired private val priceService: PriceService) {
    fun getOffers(filter: Filter, pagesAmount: Int = 10): List<Offer> {

    }
}