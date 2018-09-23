package zhi.yest.rocketleaguetracker.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.service.OfferService

@RestController
@RequestMapping("/offer")
class OfferController
@Autowired constructor(private val offerService: OfferService) {
    fun offers(id: Int, searchType: Int, pagesNum: Int): List<Offer> {
        return offerService.getOffers(Filter(id, 0, 0, searchType), pagesNum)
    }
}