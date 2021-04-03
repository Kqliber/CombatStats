package me.kaliber.combatstats.extensions

import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

fun CommandSender.message(message: List<String>, player: OfflinePlayer = this as OfflinePlayer)
{
    message.forEach { message(setMessage(player, it)) }
}

fun CommandSender.message(message: String)
{
    sendMessage(message.color())
}
