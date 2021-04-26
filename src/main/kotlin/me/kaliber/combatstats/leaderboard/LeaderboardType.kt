package me.kaliber.combatstats.leaderboard

/**
 * Allows further expansion in future
 */

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
