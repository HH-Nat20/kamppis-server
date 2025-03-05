package nat20.kamppisserver.domain.enums

enum class Cleanliness(val description: String) {
    SPOTLESS("I keep everything extremely tidy and organized at all times"),
    TIDY("I prefer a clean and well-maintained living space with regular cleaning"),
    CASUAL("I maintain a generally clean space but I am not overly strict about it"),
    MESSY("I leave things around and don't mind occasional clutter"),
    CAREFREE("I rarely clean and don't mind a disorganized environment")
}