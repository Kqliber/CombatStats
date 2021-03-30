package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.extensions.executeCmd
import me.kaliber.combatstats.extensions.msg

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.configuration.file.FileConfiguration

class Rewards(private val config: FileConfiguration)
{

    private val console = Bukkit.getConsoleSender()
    private val rewards = config.getBoolean("rewards.enabled")

    fun runKillerCommands(killer: Player, player: Player)
    {
        if (!rewards)
        {
            return
        }

        with(config)
        {
            getStringList("rewards.killer.commands").setPlaceholders(killer, player).executeCmd(console, killer)
            getStringList("rewards.killer.messages").setPlaceholders(killer, player).msg(killer)
        }
    }

    fun runPlayerCommands(player: Player, killer: Player)
    {
        if (!rewards)
        {
            return
        }

        with(config)
        {
            getStringList("rewards.player.commands").setPlaceholders(killer, player).executeCmd(console, player)
            getStringList("rewards.player.messages").setPlaceholders(killer, player).msg(player)
        }
    }

    private fun List<String>.setPlaceholders(killer: Player, player: Player): List<String>
    {
        return map()
        {
            it.replace("%killer%", killer.name)
              .replace("%dead%", player.name)
        }
    }
}
