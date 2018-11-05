package zhi.yest.rocketleaguetracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import zhi.yest.rocketleaguetracker.domain.Filter
import zhi.yest.rocketleaguetracker.domain.Offer
import zhi.yest.rocketleaguetracker.domain.OfferItem
import zhi.yest.rocketleaguetracker.misc.Fetcher
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

private const val HAS_XPATH = """//div[@id="rlg-youritems"]"""
private const val WANTS_XPATH = """//div[@id="rlg-theiritems"]"""
private const val QUANTITY_XPATH = """//div[contains(@class,"rlg-trade-display-item__amount")]"""

@Component
class OfferService
@Autowired constructor(@Qualifier("retryingFetcher") private val fetcher: Fetcher,
                       private val priceService: PriceService,
                       @Value("\${trade.page.url}") private val tradePageUrl: String) {

    fun getOffers(filter: Filter, pagesAmount: Int = 10): List<Offer> {
        val documentBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = fetcher.fetchPage("$tradePageUrl?$filter")
                .substringAfter("</head>")
                .substringBefore("</html>")
                .let {
                    val normalizedPage = "(<main class=\"rlg-main\">.*?</main>)"
                            .toRegex(RegexOption.DOT_MATCHES_ALL)
                            .find(it)!!
                            .groups[1]!!
                            .value
                            .let { main ->
                                val selected = "selected(?=.*?</option>)".toRegex()
                                val nbsp = "&nbsp;".toRegex()
                                main.replace(selected, "")
                                        .replace(nbsp, " ")
                            }
                    documentBuilder.parse(normalizedPage.byteInputStream())
                }
        val hasItems = document.toNodeList(HAS_XPATH)
                .map { it toOfferItemWith priceService }
        val wantsItems = document.toNodeList(WANTS_XPATH)
                .map { it toOfferItemWith priceService }
        return if (hasItems.size == wantsItems.size)
            hasItems.zip(wantsItems)
                    .map { Offer(it.first, it.second) }
        else listOf()
    }
}

private fun Node.toNodeList(selector: String): List<Node> {
    val xPath = XPathFactory.newInstance().newXPath()
    return this.let { xPath.evaluate(selector, it, XPathConstants.NODESET) as NodeList }
            .toList()
}

private fun NodeList.toList(): List<Node> {
    val list = mutableListOf<Node>()
    for (i in 0 until this.length) {
        list.add(this.item(i))
    }
    return list
}

private infix fun Node.toOfferItemWith(priceService: PriceService): OfferItem {
    this as Element
    val quantity = this.toNodeList(zhi.yest.rocketleaguetracker.service.QUANTITY_XPATH)
            .map { it.firstChild.nodeValue.trim().toInt() }[0]
    val name = this.getElementsByTagName("h2")
            .toList()
            .map { it.firstChild.nodeValue }[0]
    val filter = this.getElementsByTagName("a")
            .toList()
            .map { it.attributes.getNamedItem("href").nodeValue }[0]
            .toFilter()

    val price = priceService.getPrice(name)
    return OfferItem(filter.filterItem,
            name,
            quantity,
            price,
            price * quantity,
            null,
            null)
    //TODO: handle cert & color
}

private fun String.toFilter(): Filter {
    val regex = Regex("""\?filterItem=([0-9]+)(?:&amp;|&)filterCertification=([0-9]+)(?:&amp;|&)filterPaint=([0-9]+)(?:&amp;|&)filterPlatform=1""")
    val values = regex.find(this)
            ?.groupValues
    return if (values != null) Filter(values[1].toInt(),
            values[2].toInt(),
            values[3].toInt(),
            1)
    else throw RuntimeException("Cannot parse item")
}