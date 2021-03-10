package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.color

import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand

import org.bukkit.command.CommandSender

import java.io.File

@Command("combatstats")
@Alias("cstats")
class ReloadCommand(private val plugin: CombatStatsPlugin) : CommandBase()
{

    private val file = File(plugin.dataFolder, "config.yml")
    @SubCommand("reload")
    @Permission("combatstats.admin")
    fun reload(sender: CommandSender)
    {
        val message = "&bConfig reloaded.".color()

        if (file.exists())
        {
            plugin.reloadConfig()
            plugin.saveConfig()
            sender.sendMessage(message)
            return
        }

        file.parentFile.mkdirs()
        file.createNewFile()
        plugin.saveDefaultConfig()
        plugin.saveConfig()

        sender.sendMessage(message)
    }
}
