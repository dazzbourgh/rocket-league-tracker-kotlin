package zhi.yest.rocketleaguetracker.misc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component("oneShotFetcher")
class OneShotFetcher(@Autowired private val restTemplate: RestTemplate) : Fetcher {
    override fun fetchPage(url: String): String {
        return restTemplate.getForEntity(url, String::class.java).body ?: ""
    }
}