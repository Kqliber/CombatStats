package me.kaliber.combatstats.commands

import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.executeMsg

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Permission

@Command("combatstats")
@Alias("cstats")
class MainCommand(private val plugin: CombatStatsPlugin) : CommandBase()
{

    @Default
    @Permission("combatstats.info")
    fun mainCommand(sender: CommandSender)
    {
        listOf("&bCombatStats &7version &f${plugin.description.version}",
               "&7Created by: &bKaliber").executeMsg(sender as Player)
    }
}
