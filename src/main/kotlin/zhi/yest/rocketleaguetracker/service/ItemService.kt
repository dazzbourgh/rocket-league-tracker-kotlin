package zhi.yest.rocketleaguetracker.service

import org.springframework.stereotype.Service
import zhi.yest.rocketleaguetracker.domain.Item
import javax.annotation.PostConstruct

@Service
class ItemService {
    private lateinit var items: List<Item>

    @PostConstruct
    fun init() {

    }

    fun getItems(itemName: String): List<Item> {
        when (itemName) {
            "" -> return emptyList()
            else -> return items.filter {
                it.name.toLowerCase().startsWith(itemName.toLowerCase())
            }
        }
    }
}