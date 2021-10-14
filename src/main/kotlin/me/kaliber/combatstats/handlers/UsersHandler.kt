package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.extensions.getPlayer
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.user.User
import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.data.base.UserStorage
import me.kaliber.combatstats.data.impl.UserStorageGsonImpl
import me.kaliber.combatstats.data.impl.UserStorageMySQLImpl
import org.bukkit.OfflinePlayer
import java.util.UUID

class UsersHandler(private val plugin: CombatStatsPlugin)
{

    private val users = mutableMapOf<UUID, User>()
    val values: Set<User>
        get() = users.values.toSet()

    // TODO: make this not a getter. variable is initialized before Config
    val storage: UserStorage
        get() = if (Config.STORAGE_METHOD.string == "mysql") UserStorageMySQLImpl() else UserStorageGsonImpl(plugin)

    operator fun get(uuid: UUID): User
    {
        return users.getOrPut(uuid)
        {
            User(uuid)
        }
    }

    operator fun get(player: OfflinePlayer): User
    {
        return get(player.uniqueId)
    }

    operator fun get(name: String): User?
    {
        return name.getPlayer()?.let { get(it) }
    }

    operator fun set(uuid: UUID, user: User)
    {
        users[uuid] = user
    }

    fun sortLeaderboard(type: LeaderboardType): List<User>
    {
        return LeaderboardSorter().sort(type, users.values.toSet())
    }

}
