package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.user.User
import java.time.Duration
import java.time.Instant

class LeaderboardSorter
{

    fun sort(type: LeaderboardType, values: Collection<User>): List<User>
    {
        return when (type)
        {
            LeaderboardType.KILLSTREAK_ALLTIME -> sortKillstreaks(values)
            LeaderboardType.HIGHESTKILLSTREAK_ALLTIME -> sortHighestKillstreaks(values)
            LeaderboardType.KDR_ALLTIME -> sortKDRs(values)
            else -> sortKills(values, type.time)
        }
    }

    /**
     * The users returned in this list are different objects from the users stored in [UsersHandler.users]
     */
    private fun sortKills(values: Collection<User>, epoch: Long): List<User>
    {
        val since = if (epoch == 0L) 0L else Instant.now().minus(Duration.ofMillis(epoch)).toEpochMilli() // there's probably a better way
        return values
            .map()
            { user ->
                User(user.uuid,
                    user.killstreak,
                    user.highestKillstreak,
                    user.lastKill,
                    user.kills.filter { it >= since }.toMutableList(),
                    user.deaths)
            }
            .sortedByDescending { it.kills.size }
    }

    private fun sortKillstreaks(values: Collection<User>): List<User>
    {
        return values.sortedByDescending(User::killstreak)
    }

    private fun sortHighestKillstreaks(values: Collection<User>): List<User>
    {
        return values.sortedByDescending(User::highestKillstreak)
    }

    private fun sortKDRs(values: Collection<User>): List<User>
    {
        return values.sortedByDescending(User::kdr)
    }

}
