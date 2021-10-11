package me.kaliber.combatstats.placeholders

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.user.User

class CombatPlaceholders(private val plugin: CombatStatsPlugin) : AbstractExpansion("combatstats", plugin)
{

    override fun request(user: User, input: String): Any?
    {
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
            "kills" -> user.kills
            "deaths" -> user.deaths
            "kdr" -> user.kdr
            "killstreak" -> user.killstreak
            "highestkillstreak" -> user.highestKillstreak
            "last_kill" -> user.lastKill
            else -> null
        }
    }

    private fun getTop(input: String): Any?
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
                    LeaderboardType.KILLS -> user.kills
                    LeaderboardType.KILLSTREAK -> user.killstreak
                    LeaderboardType.HIGHESTKILLSTREAK -> user.highestKillstreak
                    LeaderboardType.KDR -> user.kdr
                }
            }
            else -> null
        }
    }

    private fun getPlacement(input: String): Int?
    {
        val (type, username) = input.split('_').takeIf { it.size >= 2 } ?: return null
        val user = plugin.usersHandler[username] ?: return null
        val leaderboard = LeaderboardType.match(type)?.let(plugin.leaderboardHandler::get) ?: return null

        return leaderboard.getPlacement(user)
    }

    private fun round(number: Double, input: String): String
    {
        if (input.isEmpty())
        {
            return String.format("%.2f", number)
        }

        val decimals = input.substringAfter('_').toIntOrNull() ?: 2
        return String.format("%.${decimals}f", number)
    }
}
