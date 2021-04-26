package me.kaliber.combatstats.user

import java.util.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

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
