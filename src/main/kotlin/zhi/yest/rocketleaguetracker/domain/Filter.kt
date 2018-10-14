package zhi.yest.rocketleaguetracker.domain

data class Filter(val filterItem: Int,
                  val filterCertification: Int,
                  val filterPaint: Int,
                  val filterSearchType: Int) {
    override fun toString(): String {
        return "filterItem=$filterItem&" +
                "filterCertification=$filterCertification&" +
                "filterPaint=$filterPaint&" +
                "filterPlatform=1&" +
                "filterSearchType=$filterSearchType"
    }
}