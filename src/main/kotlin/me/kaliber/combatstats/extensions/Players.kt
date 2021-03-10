package me.kaliber.combatstats.extensions

import org.bukkit.entity.Player
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

fun Player.msg(message: List<String>)
{
    message.forEach { msg(it) }
}

fun Player.msg(message: String)
{
    sendMessage(setMessage(this, message))
}

fun CommandSender.msg(player: OfflinePlayer, message: List<String>)
{
    message.forEach { sendMessage(setMessage(player, it)) }
}

fun CommandSender.msg(message: String)
{
    sendMessage(message.color())
}
