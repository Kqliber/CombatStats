package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.user.User

class LeaderboardSorter
{

    fun sort(type: LeaderboardType, values: Collection<User>): List<User>
    {
        return when (type)
        {
            LeaderboardType.KILLS -> sortKills(values)
            LeaderboardType.KILLSTREAK -> sortKillstreaks(values)
            LeaderboardType.HIGHESTKILLSTREAK -> sortHighestKillstreaks(values)
            else -> sortKDRs(values)
        }
    }

    private fun sortKills(values: Collection<User>): List<User>
    {
        return values.distinct().sortedByDescending { it.kills }
    }

    private fun sortKillstreaks(values: Collection<User>): List<User>
    {
        return values.distinct().sortedByDescending { it.killstreak }
    }

    private fun sortHighestKillstreaks(values: Collection<User>): List<User>
    {
        return values.distinct().sortedByDescending { it.highestKillstreak }
    }

    private fun sortKDRs(values: Collection<User>): List<User>
    {
        return values.distinct().sortedByDescending { it.kdr }
    }

}
