package me.kaliber.combatstats.extensions

import me.kaliber.combatstats.CombatStatsPlugin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import net.md_5.bungee.api.ChatColor

import me.clip.placeholderapi.PlaceholderAPI

private val plugin = JavaPlugin.getPlugin(CombatStatsPlugin::class.java)

/**
 * Removed hex colors until migration to adventure api
 */
fun String.color(): String
{
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun setMessage(player: OfflinePlayer, message: String): String {
    return PlaceholderAPI.setPlaceholders(player, message.replacePlaceholders(player).color())
}

private fun String.replacePlaceholders(player: OfflinePlayer): String
{
    val user = plugin.usersHandler[player]
    val otherPlayer = plugin.usersHandler[Bukkit.getOfflinePlayer(user.lastKill)] // disgusting but gets the opposing players name
    return replace("%kills%", user.kills().toString())
           .replace("%killstreak%", user.killstreak.toString())
           .replace("%killer%", otherPlayer.name())
}

fun List<String>.msg(sender: CommandSender)
{
    forEach { sender.sendMessage(it.color()) }
}

fun List<String>.executeCmd(sender: CommandSender, player: Player)
{
    forEach { Bukkit.dispatchCommand(sender, setMessage(player, it)) }
}
