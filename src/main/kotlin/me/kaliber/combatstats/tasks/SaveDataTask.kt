package me.kaliber.combatstats.tasks

import me.kaliber.combatstats.CombatStatsPlugin
import org.bukkit.scheduler.BukkitRunnable

class SaveDataTask(private val plugin: CombatStatsPlugin) : BukkitRunnable()
{
    override fun run()
    {
        plugin.usersHandler.storage.saveAll()
    }
}
