package zhi.yest.rocketleaguetracker.domain

data class OfferItem (
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Float,
    val totalPrice: Float,
    val certification: Certification?,
    val color: Color?
)

enum class Certification {
    STRIKER
}

enum class Color {
    TITANUM_WHITE
}