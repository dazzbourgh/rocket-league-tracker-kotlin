package zhi.yest.rocketleaguetracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zhi.yest.rocketleaguetracker.misc.DomFetcher
import javax.annotation.PostConstruct


const val PRICE_URL = "https://rltracker.pro/prices"

@Service
class PriceService(@Autowired private val fetcher: DomFetcher) {
    private val priceMap = mutableMapOf<String, Float>()

    @PostConstruct
    fun init() {
        val doc = fetcher.fetchPage(PRICE_URL)
        val elements = doc.select(".col-price")
        elements.forEach { element ->
            val name = element.select("h4>a").text()
            val price = element.select(".footer")
                    .text()
                    .trim()
                    .toFloat()
            priceMap[name] = price
        }
    }

    fun getPrice(name: String): Float {
        return priceMap[name]!!
    }
}