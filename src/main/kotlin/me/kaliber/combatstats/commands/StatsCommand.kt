package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.extensions.getPlayer
import me.kaliber.combatstats.extensions.msg

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
            sender.sendMessage("Player command only.")
            return
        }

        val message = plugin.config.getStringList("stats-command")
        if (arg == null)
        {
            return message.msg(sender)
        }

        val playerArg = arg.getPlayer() ?: return sender.msg("Player not found.")
        return sender.msg(playerArg, message)
    }
}
