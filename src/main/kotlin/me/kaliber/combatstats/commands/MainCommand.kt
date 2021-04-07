package me.kaliber.combatstats.commands

import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.message
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.base.CommandBase
import org.bukkit.command.CommandSender

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
