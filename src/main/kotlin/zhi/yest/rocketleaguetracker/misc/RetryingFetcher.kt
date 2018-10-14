package zhi.yest.rocketleaguetracker.misc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component("retryingFetcher")
class RetryingFetcher(@Autowired private val restTemplate: RestTemplate) : Fetcher {
    @Retryable(maxAttempts = 5)
    override fun fetchPage(url: String): String {
        return restTemplate.getForObject(url) ?: ""
    }
}