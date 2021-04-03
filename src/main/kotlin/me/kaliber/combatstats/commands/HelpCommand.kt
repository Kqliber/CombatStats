package me.kaliber.combatstats.commands

import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.message

import org.bukkit.command.CommandSender

import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase

@Command("combatstats")
@Alias("cstats")
class HelpCommand : CommandBase()
{

    @SubCommand("help")
    @Permission("combatstats.help")
    fun helpCommand(sender: CommandSender)
    {
        Config.HELP_COMMAND.list.message(sender)
    }
}
