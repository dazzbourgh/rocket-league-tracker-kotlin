package zhi.yest.rocketleaguetracker.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.service.OfferService

@RestController
@RequestMapping("/offer")
class OfferController
@Autowired constructor(private val offerService: OfferService) {
    @GetMapping
    fun offers(id: Int, searchType: Int, pagesNum: Int): List<Offer> {
        val offers = offerService.getOffers(Filter(id, 0, 0, searchType), pagesNum)
        return if (searchType == 0) offers.sortedBy { it.has.totalPrice }
        else offers.sortedBy { it.wants.totalPrice }
    }
}