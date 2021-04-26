package me.kaliber.combatstats.leaderboard

enum class LeaderboardType {
    KILLS,
    KILLSTREAK,
    KD;

    companion object
    {

        fun match(type: String): LeaderboardType?
        {
            return values().find { type.equals(it.name, true) }
        }
    }
}
