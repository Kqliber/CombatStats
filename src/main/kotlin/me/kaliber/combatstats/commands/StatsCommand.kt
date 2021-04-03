package me.kaliber.combatstats.commands

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
class StatsCommand : CommandBase()
{

    @SubCommand("stats")
    @Permission("combatstats.stats")
    fun stats(sender: CommandSender, @Optional arg: String?)
    {
        if (sender !is Player)
        {
            return Config.PLAYER_COMMAND_ONLY.string.message(sender)
        }

        val message = Config.STATS_COMMAND.list
        if (arg == null)
        {
            return sender.message(message)
        }

        val playerArg = arg.getPlayer() ?: return Config.PLAYER_NOT_FOUND.string.message(sender)
        return sender.message(message, playerArg)
    }
}
