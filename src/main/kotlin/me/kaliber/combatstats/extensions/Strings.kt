package me.kaliber.combatstats.extensions

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

lateinit var adventure: BukkitAudiences

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
    adventure.sender(sender).sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(this))
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
