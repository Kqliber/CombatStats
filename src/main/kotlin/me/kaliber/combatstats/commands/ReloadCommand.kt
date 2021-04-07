package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.extensions.message
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase
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
        val message = Config.RELOAD_COMMAND.string

        if (file.exists())
        {
            plugin.conf.reload()
            return message.message(sender)
        }

        file.parentFile.mkdirs()
        file.createNewFile()
        plugin.saveDefaultConfig()
        plugin.saveConfig()

        message.message(sender)
    }
}
