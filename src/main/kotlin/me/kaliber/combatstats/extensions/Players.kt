package me.kaliber.combatstats.extensions

import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

fun CommandSender.msg(player: OfflinePlayer, message: List<String>)
{
    message.forEach { sendMessage(setMessage(player, it)) }
}

fun CommandSender.msg(message: String)
{
    sendMessage(message.color())
}
