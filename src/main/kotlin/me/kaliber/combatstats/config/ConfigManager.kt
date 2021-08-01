package me.kaliber.combatstats.config

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.extensions.message

class ConfigManager(private val plugin: CombatStatsPlugin)
{

    fun reload()
    {
        plugin.reloadConfig()
        plugin.saveConfig()
        val config = plugin.config

        Config.values().forEach()
        {
            if (config[it.path] == null)
            {
                config[it.path] = it.default
            }
            it.load(config)
        }
        config.save(plugin.dataFolder.resolve("config.yml"))
        registerCmdUsages()
    }

    private fun registerCmdUsages()
    {
        with(plugin.commandManager.messageHandler)
        {
            register("cmd.no.permission") { Config.NO_PERMISSION.string.message(it) }
            register("cmd.no.exists") { Config.WRONG_USAGE.string.message(it) }
        }
    }
}
