package zhi.yest.rocketleaguetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication
@EnableRetry
class RocketLeagueTrackerApplication

fun main(args: Array<String>) {
    runApplication<RocketLeagueTrackerApplication>(*args)
}
