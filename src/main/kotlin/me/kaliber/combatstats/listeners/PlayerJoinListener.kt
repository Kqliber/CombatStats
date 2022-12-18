package me.kaliber.combatstats.listeners

import me.kaliber.combatstats.CombatStatsPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val plugin: CombatStatsPlugin) : Listener
{

    @EventHandler
    fun PlayerJoinEvent.onJoin()
    {
        plugin.usersHandler.storage.load(player.uniqueId)
    }
}
