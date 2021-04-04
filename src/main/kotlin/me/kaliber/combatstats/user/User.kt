package me.kaliber.combatstats.user

import org.bukkit.OfflinePlayer
import org.bukkit.Bukkit
import java.util.UUID

data class User(
    val uuid: UUID,
    var killstreak: Int,
    var lastKill: String,
    var kills: Int,
    var deaths: Int
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
