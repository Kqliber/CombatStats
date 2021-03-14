package me.kaliber.combatstats.leaderboard

import me.kaliber.combatstats.user.User

data class Leaderboard(val type: LeaderboardType, val data: List<User>)
{
    fun getEntry(placement: Int): User
    {
        return data[placement - 1]
    }

    fun getPlacement(user: User): Int
    {
        return data.indexOf(user)
    }
}
