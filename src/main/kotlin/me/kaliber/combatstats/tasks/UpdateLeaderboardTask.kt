package me.kaliber.combatstats.tasks

import me.kaliber.combatstats.CombatStatsPlugin
import org.bukkit.scheduler.BukkitRunnable

class UpdateLeaderboardTask(private val plugin: CombatStatsPlugin) : BukkitRunnable()
{
    override fun run()
    {
        plugin.leaderboardHandler.update()
    }
}
