package me.kaliber.combatstats.listeners

import me.kaliber.combatstats.handlers.Rewards
import me.kaliber.combatstats.CombatStatsPlugin

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener(private val plugin: CombatStatsPlugin) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun PlayerDeathEvent.onDeath()
    {
        val player = plugin.usersHandler[entity]
        val possible = entity.killer

        if (possible == null)
        {
            player.reset()
            return
        }

        val killer = plugin.usersHandler[possible]
        // entering kill streak statistics
        killer.killstreak++
        player.reset()

        // set player's last known kill username
        killer.lastKill = player.name()

        // running commands and messages in config
        with(Rewards(plugin.config))
        {
        runKillerCommands(possible, entity)
        runPlayerCommands(entity, possible)
        }
    }
}
