package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.leaderboard.Leaderboard
import me.kaliber.combatstats.leaderboard.LeaderboardType

class LeaderboardHandler(private val plugin: CombatStatsPlugin)
{

    private val leaderboard = mutableMapOf<LeaderboardType, Leaderboard>()

    fun update()
    {
        LeaderboardType.values().forEach()
        {
            when(it)
            {
                LeaderboardType.KILLS -> leaderboard[it] = Leaderboard(it, plugin.usersHandler.getKills())
                LeaderboardType.KILLSTREAK -> leaderboard[it] = Leaderboard(it, plugin.usersHandler.getKillstreaks())
            }
        }
    }

    operator fun get(type: LeaderboardType): Leaderboard?
    {
        return leaderboard[type]
    }
}
