package zhi.yest.rocketleaguetracker.configuration

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {
    @Bean
    fun client(): HttpClient {
        val userAgent = BasicHeader(HttpHeaders.USER_AGENT, """Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36""")
        return HttpClients.custom()
                .setDefaultHeaders(listOf(userAgent))
                .build()
    }

    @Bean
    @Autowired
    fun requestFactory(client: HttpClient): ClientHttpRequestFactory {
        return HttpComponentsClientHttpRequestFactory(client)
    }

    @Bean
    @Autowired
    fun restTemplate(clientHttpRequestFactory: ClientHttpRequestFactory): RestTemplate {
        return RestTemplateBuilder()
                .requestFactory { clientHttpRequestFactory }
                .build()
    }
}