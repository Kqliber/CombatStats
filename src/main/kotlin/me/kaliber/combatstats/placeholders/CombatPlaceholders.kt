package me.kaliber.combatstats.placeholders

import org.bukkit.OfflinePlayer

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.kaliber.combatstats.CombatStatsPlugin

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

            /**
             * Add leaderboard again
            input.startsWith("killstreak_leaderboard_") ->
            {
                val args = input.substringAfter("killstreak_leaderboard_").split('_')

                if (args[0] == "name")
                {
                    return args[1]
                }
                if (args[0] == "score")
                {
                    return args[1]
                }
                if (args[0] == "find")
                {
                    return args[1]
                }
            }
            **/

            input == "last_kill" ->
            {
                return user.lastKill
            }
        }
        return null
    }
}
