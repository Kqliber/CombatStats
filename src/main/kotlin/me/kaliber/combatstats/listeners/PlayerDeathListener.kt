package me.kaliber.combatstats.listeners

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.handlers.RewardsHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener(private val plugin: CombatStatsPlugin) : Listener
{

    @EventHandler(ignoreCancelled = true)
    fun PlayerDeathEvent.onDeath()
    {
        val player = plugin.usersHandler[entity]
        val killer = entity.killer ?: return run()
        {
            player.reset()
            player.deaths++
        }

        val user = plugin.usersHandler[killer]
        user.killstreak++

        if (user.killstreak > user.highestKillstreak)
        {
            user.highestKillstreak = user.killstreak
        }

        user.kills.add(System.currentTimeMillis())
        user.lastKill = player.name
        user.lastKillHealth = killer.health

        player.reset()
        player.deaths++

        with(RewardsHandler(plugin))
        {
            runKillstreakRewards(killer, entity)
            runKillerCommands(killer, entity)
            runPlayerCommands(entity, killer)
        }
    }
}
