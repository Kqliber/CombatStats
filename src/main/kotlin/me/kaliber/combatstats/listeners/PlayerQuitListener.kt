package me.kaliber.combatstats.listeners

import me.kaliber.combatstats.CombatStatsPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(private val plugin: CombatStatsPlugin) : Listener
{

    @EventHandler
    fun PlayerQuitEvent.onLeave()
    {
        val user = plugin.usersHandler[player]
        plugin.usersHandler.storage.save(user)
    }
}
