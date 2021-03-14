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

        when {
            input == "kills" -> return kills.toString()
            input == "deaths" -> return deaths.toString()
            input == "kd" -> return kd.toString()
            input.startsWith("kd_rounded_") ->
            {
                val decimalPlace = input.substringAfter("kd_rounded_").toIntOrNull() ?: return null
                return String.format("%.${decimalPlace}f", kd)
            }

            input == "killstreak" ->
            {
                return user.killstreak.toString()
            }

            input.startsWith("top_") ->
            {
                val args = input.substringAfter("top_").split('_')
                if (args.size > 2)
                {
                    return null
                }

                val type = LeaderboardType.match(args[0]) ?: return null
                val leaderboard = plugin.leaderboardHandler.getLeaderboard(type) ?: return null
                if (args[1] == "name")
                {
                    return args[2].toIntOrNull()?.let(leaderboard::getEntry)?.name()
                }
                if (args[1] == "kills")
                {
                    return args[2].toIntOrNull()?.let(leaderboard::getEntry)?.kills().toString()
                }
                return args[2].toIntOrNull()?.let(leaderboard::getEntry)?.killstreak.toString()
            }

            input.startsWith("placement_") ->
            {
                val args = input.substringAfter("placement_").split('_')
                val type = LeaderboardType.match(args[0]) ?: return null
                val username = plugin.usersHandler[args[1]]
                return plugin.leaderboardHandler.getLeaderboard(type)?.getPlacement(username).toString()
            }

            input == "last_kill" ->
            {
                return user.lastKill
            }
        }
        return null
    }
}
