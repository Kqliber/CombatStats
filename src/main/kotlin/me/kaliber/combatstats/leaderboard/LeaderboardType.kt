package me.kaliber.combatstats.leaderboard

import java.time.Instant
import java.util.concurrent.TimeUnit

enum class LeaderboardType(val time: Long)
{
    KILLS_DAILY(TimeUnit.DAYS.toMillis(1)),
    KILLS_WEEKLY(TimeUnit.DAYS.toMillis(7)),
    KILLS_MONTHLY(TimeUnit.DAYS.toMillis(30)),
    KILLS_ALLTIME(Instant.EPOCH.toEpochMilli()),
    KILLSTREAK_ALLTIME(Instant.EPOCH.toEpochMilli()),
    HIGHESTKILLSTREAK_ALLTIME(Instant.EPOCH.toEpochMilli()),
    KDR_ALLTIME(Instant.EPOCH.toEpochMilli());

    companion object
    {
        val VALUES = values().toList()

        fun match(type: String): LeaderboardType?
        {
            return VALUES.find { type.equals(it.name, true) }
        }
    }
}
