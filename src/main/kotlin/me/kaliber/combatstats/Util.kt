package me.kaliber.combatstats

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.command.CommandSender
import org.bukkit.Bukkit
import net.md_5.bungee.api.ChatColor
import org.bukkit.plugin.java.JavaPlugin
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

fun Player.msg(message: String)
{
    sendMessage(setMessage(this, message))
}

fun CommandSender.msg(player: OfflinePlayer, message: String)
{
    sendMessage(setMessage(player, message))
}

private fun String.replacePlaceholders(player: OfflinePlayer): String
{
    val user = plugin.usersHandler[player]
    val otherPlayer = plugin.usersHandler[Bukkit.getOfflinePlayer(user.lastKill)] // disgusting but gets the opposing players name
    return replace("%kills%", user.kills().toString())
           .replace("%killstreak%", user.killstreak.toString())
           .replace("%killer%", otherPlayer.name())
}

fun List<String>.executeCmd(sender: CommandSender, player: Player)
{
    forEach()
    {
        Bukkit.dispatchCommand(sender, setMessage(player, it))
    }
}

fun List<String>.executeMsg(player: Player)
{
    forEach()
    {
        player.msg(it)
    }
}
