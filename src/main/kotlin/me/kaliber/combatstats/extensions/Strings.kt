package me.kaliber.combatstats.extensions

import org.bukkit.entity.Player
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.Bukkit
import net.md_5.bungee.api.ChatColor

import me.clip.placeholderapi.PlaceholderAPI

/**
 * Removed hex colors until migration to adventure api
 */
fun String.color(): String
{
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun setMessage(player: OfflinePlayer, message: String): String {
    return PlaceholderAPI.setPlaceholders(player, message.color())
}

fun List<String>.message(sender: CommandSender)
{
    forEach { it.message(sender) }
}

fun String.message(sender: CommandSender)
{
    sender.sendMessage(this.color())
}

fun List<String>.executeCmd(sender: CommandSender, player: Player)
{
    forEach { Bukkit.dispatchCommand(sender, setMessage(player, it)) }
}

fun String.getPlayer(): OfflinePlayer?
{
    Bukkit.getOfflinePlayers().forEach()
    {
        if (it.name == this)
        {
            return it
        }
    }
    return null
}
