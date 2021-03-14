package me.kaliber.combatstats.leaderboard

enum class LeaderboardType {
    KILLS,
    KILLSTREAK;

    companion object
    {
        val values = values()

        fun match(type: String): LeaderboardType?
        {
            return values.find { type.equals(it.name, true) }
        }
    }
}
