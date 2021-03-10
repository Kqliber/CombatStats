package me.kaliber.combatstats.user

import org.bukkit.OfflinePlayer
import org.bukkit.Statistic
import org.bukkit.Bukkit
import java.util.UUID

data class User(val uuid: UUID, var killstreak: Int, var lastKill: String)
{

    fun player(): OfflinePlayer
    {
        return Bukkit.getOfflinePlayer(uuid)
    }

    fun name(): String
    {
        return player().name ?: ""
    }

    fun reset()
    {
        killstreak = 0
    }

    fun kills(): Int
    {
        return player().getStatistic(Statistic.PLAYER_KILLS)
    }

    fun deaths(): Int
    {
        return player().getStatistic(Statistic.DEATHS)
    }
}
