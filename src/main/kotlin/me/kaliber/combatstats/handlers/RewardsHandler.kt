package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.executeCmd
import me.kaliber.combatstats.executeMsg

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.Bukkit

class Rewards(private val config: FileConfiguration)
{

    private val console = Bukkit.getConsoleSender()
    private val rewards = config.getBoolean("rewards.enabled")

    fun runKillerCommands(killer: Player)
    {
        if (!rewards)
        {
            return
        }

        with(config)
        {
            getStringList("rewards.killer.commands").executeCmd(console, killer)
            getStringList("rewards.killer.messages").executeMsg(killer)
        }
    }

    fun runPlayerCommands(player: Player)
    {
        if (!rewards)
        {
            return
        }

        with(config)
        {
            getStringList("rewards.player.commands").executeCmd(console, player)
            getStringList("rewards.player.messages").executeMsg(player)
        }
    }
}
