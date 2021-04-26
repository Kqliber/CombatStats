package me.kaliber.combatstats.user

import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

data class User(
    val uuid: UUID,
    var killstreak: Int = 0,
    var highestKillstreak: Int = 0,
    var lastKill: String = "",
    var kills: Int = 0,
    var deaths: Int = 0
    )
{

    val kdr: Double
        get() = if (deaths == 0) kills.toDouble() else kills.toDouble() / deaths.toDouble()

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

}
