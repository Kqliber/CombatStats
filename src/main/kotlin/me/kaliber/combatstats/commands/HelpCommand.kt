package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.executeMsg

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase

@Command("combatstats")
@Alias("cstats")
class HelpCommand(private val plugin: CombatStatsPlugin) : CommandBase()
{

    @SubCommand("help")
    @Permission("combatstats.help")
    fun helpCommand(sender: CommandSender)
    {
        listOf("&9CombatStats Help &8(&7v${plugin.description.version}&8)",
            "&b/cs &8- &7Display plugin information",
            "&b/cs help &8- &7Display plugin commands",
            "&b/cs reload &8- &7Reload configuration file",
            "&b/cs stats <player> &8- &7Display player statistics").executeMsg(sender as Player)
    }
}
