package zhi.yest.rocketleaguetracker.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zhi.yest.rocketleaguetracker.domain.Item
import zhi.yest.rocketleaguetracker.service.ItemService

@RestController
@RequestMapping("/item")
class ItemController(@Autowired private val itemService: ItemService) {
    @GetMapping
    fun getItems(@RequestParam itemName: String): List<Item> {
        return itemService.getItems(itemName)
    }
}