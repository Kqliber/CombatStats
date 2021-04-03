package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.executeCmd
import me.kaliber.combatstats.extensions.message

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class RewardsHandler
{

    private val console = Bukkit.getConsoleSender()
    private val rewards = Config.REWARDS_ENABLED.boolean

    fun runKillerCommands(killer: Player, player: Player)
    {
        if (!rewards)
        {
            return
        }

        Config.KILLER_COMMANDS.list.setPlaceholders(killer, player).executeCmd(console, killer)
        Config.KILLER_MESSAGES.list.setPlaceholders(killer, player).message(killer)
    }

    fun runPlayerCommands(player: Player, killer: Player)
    {
        if (!rewards)
        {
            return
        }

        Config.PLAYER_COMMANDS.list.setPlaceholders(killer, player).executeCmd(console, player)
        Config.PLAYER_MESSAGES.list.setPlaceholders(killer, player).message(player)
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
