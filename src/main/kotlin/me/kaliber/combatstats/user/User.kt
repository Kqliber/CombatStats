package me.kaliber.combatstats.user

import org.bukkit.OfflinePlayer
import org.bukkit.Statistic
import org.bukkit.Bukkit
import org.bukkit.entity.Player
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

    /**
     * 1.8 only supports getting statistics from a Player instead of an OfflinePlayer.
     * anything above will allow for getting statistics even when the player is offline.
     */
    fun kills(): Int
    {
        val player = player()
        try
        {
            return player.getStatistic(Statistic.PLAYER_KILLS)
        }
        catch (exception: NoSuchMethodError)
        {
            if (player.isOnline)
            {
                return (player as Player).getStatistic(Statistic.PLAYER_KILLS)
            }
        }
        return 0
    }

    /**
     * 1.8 only supports getting statistics from a Player instead of an OfflinePlayer.
     * any version above will allow for getting statistics even when the player is offline.
     */
    fun deaths(): Int
    {
        val player = player()
        try
        {
            return player.getStatistic(Statistic.DEATHS)
        }
        catch (exception: NoSuchMethodError)
        {
            if (player.isOnline)
            {
                return (player as Player).getStatistic(Statistic.DEATHS)
            }
        }
        return 0
    }
}
