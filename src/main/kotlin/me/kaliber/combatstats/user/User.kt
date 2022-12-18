package me.kaliber.combatstats.user

import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

data class User(
    val uuid: UUID,
    var killstreak: Int = 0,
    var highestKillstreak: Int = 0,
    var lastKill: String = "",
    var kills: MutableList<Long> = mutableListOf(),
    var deaths: Int = 0,
    var lastKillHealth: Double = 0.0
)
{

    val kdr: Double
        get() = if (deaths == 0) kills.size.toDouble() else kills.size.toDouble() / deaths.toDouble()

    val player: OfflinePlayer
        get() = Bukkit.getOfflinePlayer(uuid)

    val name: String
        get() = player.name ?: ""

    fun reset()
    {
        killstreak = 0
    }

}
