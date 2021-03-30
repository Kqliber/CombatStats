package me.kaliber.combatstats.extensions

import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

fun CommandSender.message(player: OfflinePlayer, message: List<String>)
{
    message.forEach { sendMessage(setMessage(player, it)) }
}

fun CommandSender.message(message: String)
{
    sendMessage(message.color())
}
