package me.kaliber.combatstats.listeners

import me.kaliber.combatstats.handlers.RewardsHandler
import me.kaliber.combatstats.CombatStatsPlugin

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener(private val plugin: CombatStatsPlugin) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun PlayerDeathEvent.onDeath()
    {
        val player = plugin.usersHandler[entity]
        val killer = entity.killer ?: return player.reset()

        val user = plugin.usersHandler[killer]
        user.killstreak++
        user.kills++
        user.lastKill = player.name()

        player.reset()
        player.deaths++

        with(RewardsHandler())
        {
        runKillerCommands(killer, entity)
        runPlayerCommands(entity, killer)
        }
    }
}
