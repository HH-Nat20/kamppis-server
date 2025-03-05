package nat20.kamppisserver.domain.enums

enum class MaxRent(val sign: String, val max: Int) {
    LOW("€",  400),
    MID("€€",  600),
    HIGH("€€€", 800)
}