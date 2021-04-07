package me.kaliber.combatstats.user

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

data class User(
    val uuid: UUID,
    var killstreak: Int = 0,
    var lastKill: String = "",
    var kills: Int = 0,
    var deaths: Int = 0
    )
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

}
