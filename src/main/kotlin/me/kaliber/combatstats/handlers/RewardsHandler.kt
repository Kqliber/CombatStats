package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.executeCmd
import me.kaliber.combatstats.extensions.message

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class RewardsHandler(private val plugin: CombatStatsPlugin)
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
        val message = Config.KILLER_MESSAGES.list.setPlaceholders(killer, player)
        killer.message(message)
    }

    fun runPlayerCommands(player: Player, killer: Player)
    {
        if (!rewards)
        {
            return
        }

        Config.PLAYER_COMMANDS.list.setPlaceholders(killer, player).executeCmd(console, player)
        val message = Config.PLAYER_MESSAGES.list.setPlaceholders(killer, player)
        player.message(message)
    }

    fun runKillstreakRewards(killer: Player, player: Player)
    {
        if (!rewards)
        {
            return
        }

        val section = plugin.config.getConfigurationSection("rewards.killstreaks") ?: return
        val keys = section.getKeys(false)
        val user = plugin.usersHandler[killer]
        keys.forEach()
        {
            if (user.killstreak.toString() != it)
            {
                return@forEach
            }
            plugin.config.getStringList("rewards.killstreaks.$it.commands").executeCmd(console, killer)
            val message = plugin.config.getStringList("rewards.killstreaks.$it.messages").setPlaceholders(killer, player)
            killer.message(message)
        }
    }

    private fun List<String>.setPlaceholders(killer: Player, player: Player): List<String>
    {
        if (isEmpty())
        {
            return emptyList()
        }
        return map()
        {
            it.replace("%killer%", killer.name)
              .replace("%dead%", player.name)
        }
    }
}
