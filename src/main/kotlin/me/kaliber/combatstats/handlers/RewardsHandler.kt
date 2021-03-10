package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.extensions.msg
import me.kaliber.combatstats.extensions.executeCmd

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.configuration.file.FileConfiguration

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
            getStringList("rewards.killer.messages").msg(killer)
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
            getStringList("rewards.player.messages").msg(player)
        }
    }
}
