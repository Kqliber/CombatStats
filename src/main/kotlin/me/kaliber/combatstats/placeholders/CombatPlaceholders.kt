package me.kaliber.combatstats.placeholders

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.user.User
import java.math.RoundingMode
import java.text.DecimalFormat

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
            "kills" -> user.kills.size
            "deaths" -> user.deaths
            "kdr" -> user.kdr
            "killstreak" -> user.killstreak
            "highestkillstreak" -> user.highestKillstreak
            "last_kill" -> user.lastKill
            "last_kill_health" -> user.lastKillHealth
            "last_kill_hearts" -> {
                val format = DecimalFormat("#.#").also { it.roundingMode = RoundingMode.HALF_UP }
                format.format(user.lastKillHealth / 2)
            }
            else -> null
        }
    }

    private fun getTop(input: String): Any?
    {
        val (type, dateType, info, position) = input.split('_').takeIf { it.size >= 4 } ?: return null

        val leaderboardType = type + '_' + dateType
        val leaderboard = LeaderboardType.match(leaderboardType)?.let(plugin.leaderboardHandler::get) ?: return null
        val user = position.toIntOrNull()?.let(leaderboard::getEntry) ?: return null

        return when (info)
        {
            "name" -> user.name
            "value" ->
            {
                return when (leaderboard.type)
                {
                    LeaderboardType.KILLSTREAK_ALLTIME -> user.killstreak
                    LeaderboardType.HIGHESTKILLSTREAK_ALLTIME -> user.highestKillstreak
                    LeaderboardType.KDR_ALLTIME -> user.kdr
                    else -> user.kills.size
                }
            }
            else -> null
        }
    }

    private fun getPlacement(input: String): Int?
    {
        val (type, dateType, username) = input.split('_').takeIf { it.size >= 3 } ?: return null
        val leaderboardType = type + '_' + dateType
        val user = plugin.usersHandler[username] ?: return null
        val leaderboard = LeaderboardType.match(leaderboardType)?.let(plugin.leaderboardHandler::get) ?: return null

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
