package me.kaliber.combatstats.commands

import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.message

import org.bukkit.command.CommandSender

import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Permission

@Command("combatstats")
@Alias("cstats")
class MainCommand : CommandBase()
{

    @Default
    @Permission("combatstats.info")
    fun mainCommand(sender: CommandSender)
    {
        Config.MAIN_COMMAND.list.message(sender)
    }
}
