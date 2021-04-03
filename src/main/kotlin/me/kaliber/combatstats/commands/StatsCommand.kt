package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.getPlayer
import me.kaliber.combatstats.extensions.message

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Optional
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand

@Command("combatstats")
class StatsCommand(private val plugin: CombatStatsPlugin) : CommandBase()
{

    @SubCommand("stats")
    @Permission("combatstats.stats")
    fun stats(sender: CommandSender, @Optional arg: String?)
    {
        if (sender !is Player)
        {
            return sender.sendMessage("Player command only.")
        }

        val message = Config.STATS_COMMAND.list
        if (arg == null)
        {
            return message.message(sender)
        }

        val playerArg = arg.getPlayer() ?: return sender.message("Player not found.")
        return sender.message(playerArg, message)
    }
}
