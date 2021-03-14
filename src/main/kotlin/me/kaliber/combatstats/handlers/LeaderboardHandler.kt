package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.leaderboard.Leaderboard
import me.kaliber.combatstats.leaderboard.LeaderboardType

/**
 * find a good way to do this
 */
class LeaderboardHandler(private val plugin: CombatStatsPlugin)
{

    private val leaderboard = mutableMapOf<LeaderboardType, Leaderboard>()

    fun update()
    {
        LeaderboardType.values.forEach()
        {
            if (it == LeaderboardType.KILLS)
            {
                leaderboard[it] = Leaderboard(it, plugin.usersHandler.getKills())
            }
            else
            {
                leaderboard[it] = Leaderboard(it, plugin.usersHandler.getKillstreaks())
            }
        }
    }

    fun getLeaderboard(type: LeaderboardType): Leaderboard?
    {
        return leaderboard[type]
    }
}
