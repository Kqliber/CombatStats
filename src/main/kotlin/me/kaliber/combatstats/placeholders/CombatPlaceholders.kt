package me.kaliber.combatstats.placeholders

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.CombatStatsPlugin
import org.bukkit.OfflinePlayer

class CombatPlaceholders(private val plugin: CombatStatsPlugin) : PlaceholderExpansion()
{

    override fun getAuthor(): String
    {
        return "Kaliber"
    }

    override fun getVersion(): String
    {
        return plugin.description.version
    }

    override fun getIdentifier(): String
    {
        return "combatstats"
    }

    override fun canRegister(): Boolean
    {
        return true
    }

    override fun persist(): Boolean
    {
        return true
    }

    override fun onRequest(offlinePlayer: OfflinePlayer, input: String): String?
    {
        val user = plugin.usersHandler[offlinePlayer]

        if (input.startsWith("kdr_rounded"))
        {
            return round(user.kdr, input.substringAfter("kdr_rounded"))
        }

        if (input.startsWith("top_"))
        {
            return getTop(input.substringAfter("top_"))
        }

        if (input.startsWith("placement_"))
        {
            return getPlacement(input.substringAfter("placement_"))
        }

        return when (input)
        {
            "kills" -> user.kills.toString()
            "deaths" -> user.deaths.toString()
            "kdr" -> user.kdr.toString()
            "killstreak" -> user.killstreak.toString()
            "highestkillstreak" -> user.highestKillstreak.toString()
            "last_kill" -> user.lastKill
            else -> null
        }
    }

    private fun getTop(input: String): String?
    {
        val (type, info, position) = input.split('_').takeIf { it.size >= 3 } ?: return null

        val leaderboard = LeaderboardType.match(type)?.let(plugin.leaderboardHandler::get) ?: return null
        val user = position.toIntOrNull()?.let(leaderboard::getEntry) ?: return null

        return when (info)
        {
            "name" -> user.name
            "value" ->
            {
                return when (leaderboard.type)
                {
                    LeaderboardType.KILLS -> user.kills.toString()
                    LeaderboardType.KILLSTREAK -> user.killstreak.toString()
                    LeaderboardType.HIGHESTKILLSTREAK -> user.highestKillstreak.toString()
                    LeaderboardType.KDR -> user.kdr.toString()
                }
            }
            else -> null
        }
    }

    private fun getPlacement(input: String): String?
    {
        val (type, username) = input.split('_').takeIf { it.size >= 2 } ?: return null
        val user = plugin.usersHandler[username] ?: return null
        val leaderboard = LeaderboardType.match(type)?.let(plugin.leaderboardHandler::get) ?: return null

        return leaderboard.getPlacement(user).toString()
    }

    private fun round(number: Double, input: String): String
    {
        if (input.isEmpty())
        {
            return String.format("%.2f", number)
        }

        val decimals = input.substringAfter('_').toIntOrNull() ?: String.format("%.2f", number)
        return String.format("%.${decimals}f", number)
    }
}
