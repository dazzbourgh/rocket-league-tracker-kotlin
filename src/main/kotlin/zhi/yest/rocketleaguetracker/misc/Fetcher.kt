package zhi.yest.rocketleaguetracker.misc

interface Fetcher {
    fun fetchPage(url: String): String
}