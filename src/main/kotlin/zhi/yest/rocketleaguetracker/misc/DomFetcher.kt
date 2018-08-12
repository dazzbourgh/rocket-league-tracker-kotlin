package zhi.yest.rocketleaguetracker.misc

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class DomFetcher {
    fun fetchPage(url: String): Document {
        return Jsoup.connect(url).get()
    }
}