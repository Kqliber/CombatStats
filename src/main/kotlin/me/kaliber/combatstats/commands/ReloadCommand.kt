package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.extensions.message

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

    @SubCommand("reload")
    @Permission("combatstats.admin")
    fun reload(sender: CommandSender)
    {
        val file = File(plugin.dataFolder, "config.yml")
        val message = "&bConfig reloaded."

        if (file.exists())
        {
            plugin.conf.reload()
            return sender.message(message)
        }

        file.parentFile.mkdirs()
        file.createNewFile()
        plugin.saveDefaultConfig()
        plugin.saveConfig()

        sender.message(message)
    }
}
