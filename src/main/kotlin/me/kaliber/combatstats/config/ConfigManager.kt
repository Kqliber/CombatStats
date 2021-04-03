package me.kaliber.combatstats.config

import me.kaliber.combatstats.CombatStatsPlugin
import java.io.File

class ConfigManager(private val plugin: CombatStatsPlugin)
{

    fun reload()
    {
        plugin.reloadConfig()
        plugin.saveConfig()
        val config = plugin.config

        Config.values().forEach()
        {
            if (config.get(it.path) == null)
            {
                config.set(it.path, it.default)
            }
            it.load(config)
        }
        config.save(File(plugin.dataFolder, "config.yml"))
    }

}