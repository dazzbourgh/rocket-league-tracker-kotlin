package zhi.yest.rocketleaguetracker.domain

class Filter(private val filterItem: Int,
             private val filterCertification: Int,
             private val filterPaint: Int,
             private val filterSearchType: Int) {
    override fun toString(): String {
        return "filterItem=$filterItem&" +
                "filterCertification=$filterCertification&" +
                "filterPaint=$filterPaint&" +
                "filterPlatform=1&" +
                "filterSearchType=$filterSearchType";
    }
}