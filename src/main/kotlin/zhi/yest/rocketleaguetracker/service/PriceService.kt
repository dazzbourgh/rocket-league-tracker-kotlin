package zhi.yest.rocketleaguetracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import zhi.yest.rocketleaguetracker.misc.DomFetcher
import javax.annotation.PostConstruct

@Service
class PriceService(@Autowired private val fetcher: DomFetcher,
                   @Value("\${price.page.url}") private val pricePageUrl: String) {
    private val priceMap = mutableMapOf<String, Float>()

    @PostConstruct
    fun init() {
        val doc = fetcher.fetchPage(pricePageUrl)
        val elements = doc.select(".col-price")
        elements
                .map {
                    val name = it.select("h4>a").text()
                    val price = it.select(".footer")
                            .text()
                            .trim()
                    Pair(name, price)
                }
                .filter { !it.second.contains("-") }
                .forEach { priceMap[it.first] = it.second.toFloat() }
    }

    fun getPrice(name: String): Float {
        return priceMap[name]!!
    }
}