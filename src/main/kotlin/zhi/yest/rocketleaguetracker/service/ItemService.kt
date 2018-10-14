package zhi.yest.rocketleaguetracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import zhi.yest.rocketleaguetracker.domain.Item
import zhi.yest.rocketleaguetracker.misc.Fetcher
import javax.annotation.PostConstruct

private val ITEM_REGEX = Regex("""<option value="([0-9]+)">(.*?)</option>""").toPattern()

@Service
class ItemService(@Autowired @Qualifier("retryingFetcher") private val fetcher: Fetcher,
                  @Value("\${item.page.url}") private val itemPageUrl: String) {
    private val items: MutableList<Item> = mutableListOf()

    @PostConstruct
    fun init() {
        val matcher = ITEM_REGEX.matcher(fetcher.fetchPage(itemPageUrl))
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