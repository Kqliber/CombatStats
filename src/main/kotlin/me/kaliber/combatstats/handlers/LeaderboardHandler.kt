package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.leaderboard.Leaderboard
import me.kaliber.combatstats.CombatStatsPlugin

class LeaderboardHandler(private val plugin: CombatStatsPlugin)
{

    private val leaderboard = mutableMapOf<LeaderboardType, Leaderboard>()

    fun update()
    {
        LeaderboardType.values().forEach()
        {
            when(it)
            {
                LeaderboardType.KILLS -> leaderboard[it] = Leaderboard(it, plugin.usersHandler.sortLeaderboard(it))
                LeaderboardType.KILLSTREAK -> leaderboard[it] = Leaderboard(it, plugin.usersHandler.sortLeaderboard(it))
                LeaderboardType.HIGHESTKILLSTREAK -> leaderboard[it] = Leaderboard(it, plugin.usersHandler.sortLeaderboard(it))
                LeaderboardType.KDR -> leaderboard[it] = Leaderboard(it, plugin.usersHandler.sortLeaderboard(it))
            }
        }
    }

    operator fun get(type: LeaderboardType): Leaderboard?
    {
        return leaderboard[type]
    }
}
