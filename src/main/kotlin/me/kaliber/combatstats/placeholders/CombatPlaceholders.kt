package me.kaliber.combatstats.placeholders

import org.bukkit.OfflinePlayer

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.leaderboard.LeaderboardType

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
        val kills = user.kills()
        val deaths = user.deaths()
        val kd = if (deaths == 0) kills.toDouble() else kills.toDouble() / deaths.toDouble()

        if (input.startsWith("kd_rounded"))
        {
            return round(kd, input.substringAfter("kd_rounded"))
        }

        if (input.startsWith("top_"))
        {
            return getTop(input.substringAfter("top_"))
        }

        if (input.startsWith("placement_"))
        {
            return getPlacement(input.substringAfter("placement_"))
        }

        return when (input) {
            "kills" -> kills.toString()
            "deaths" -> deaths.toString()
            "kd" -> kd.toString()

            "killstreak" -> user.killstreak.toString()

            "last_kill" -> user.lastKill
            else -> null
        }

    }

    private fun getTop(input: String): String?
    {
        val args = input.substringAfter("top_").split('_')
        if (args.size < 4)
        {
            return null
        }

        val type = LeaderboardType.match(args[0]) ?: return null
        val leaderboard = plugin.leaderboardHandler.getLeaderboard(type) ?: return null

        return when (args[1])
        {
            "name" -> args[2].toIntOrNull()?.let(leaderboard::getEntry)?.name()
            "kills" -> args[2].toIntOrNull()?.let(leaderboard::getEntry)?.kills().toString()
            else -> args[2].toIntOrNull()?.let(leaderboard::getEntry)?.killstreak.toString()
        }
    }

    private fun getPlacement(input: String): String?
    {
        val args = input.split('_')
        val type = LeaderboardType.match(args[0]) ?: return null
        val username = plugin.usersHandler[args[1]]

        return plugin.leaderboardHandler.getLeaderboard(type)?.getPlacement(username).toString()
    }

    private fun round(number: Double, input: String): String?
    {
        if (input.isEmpty())
        {
            return String.format("%.2f", number)
        }

        val decimals = input.substringAfter('_').toIntOrNull() ?: return null
        return String.format("%.${decimals}f, $number")
    }
}
