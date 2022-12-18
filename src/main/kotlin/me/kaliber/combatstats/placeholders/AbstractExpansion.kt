package me.kaliber.combatstats.placeholders

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.user.User
import org.bukkit.OfflinePlayer

abstract class AbstractExpansion(
    private val identifier: String,
    private val plugin: CombatStatsPlugin
) : PlaceholderExpansion()
{
    override fun getIdentifier() = identifier

    override fun getAuthor() = plugin.description.authors[0]!!

    override fun getVersion() = plugin.description.version

    override fun canRegister() = true

    override fun persist() = true

    abstract fun request(user: User, input: String): Any?

    override fun onRequest(player: OfflinePlayer, params: String) =
        request(plugin.usersHandler[player], PlaceholderAPI.setBracketPlaceholders(player, params)).toString()
}
