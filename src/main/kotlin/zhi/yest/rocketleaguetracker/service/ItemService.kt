package zhi.yest.rocketleaguetracker.service

import org.springframework.stereotype.Service
import zhi.yest.rocketleaguetracker.domain.Item
import zhi.yest.rocketleaguetracker.misc.Fetcher
import javax.annotation.PostConstruct
import javax.annotation.Resource

const val URL = "https://rocket-league.com/trading"
val ITEM_PATTERN = Regex("""<option value="([0-9]+)">(.*?)</option>""").toPattern()

@Service
class ItemService(@Resource(name = "retryingFetcher") private val fetcher: Fetcher) {
    private val items: MutableList<Item> = mutableListOf()

    @PostConstruct
    fun init() {
        val matcher = ITEM_PATTERN.matcher(fetcher.fetchPage(URL))
        while (matcher.find()) {
            val item = Item(matcher.group(1).toInt(), matcher.group(2))
            items.add(item)
        }
    }

    fun getItems(itemName: String): List<Item> {
        return when (itemName) {
            "" -> emptyList()
            else -> items.filter {
                it.name.toLowerCase().startsWith(itemName.toLowerCase())
            }
        }
    }
}